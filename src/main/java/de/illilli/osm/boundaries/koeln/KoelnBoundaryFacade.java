package de.illilli.osm.boundaries.koeln;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.geojson.Feature;
import org.geojson.FeatureCollection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.illilli.osm.boundaries.planetOsm.GeojsonJacksonOsmFeature;
import de.illilli.osm.boundaries.planetOsm.PlanetOsmFeature;
import de.illilli.osm.boundaries.planetOsm.PlanetOsmPolygon;
import de.illilli.osm.boundaries.planetOsm.PlanetOsmPolygonDao;
import de.illilli.osm.jdbc.ConnectionFactory;
import de.illilli.osm.jdbc.koelnboundary.AskFor;

/**
 * This class retrieves boundaries for Stadtteile Koeln from OSM Database.
 */
public class KoelnBoundaryFacade implements Facade {

	private static final Logger logger = Logger
			.getLogger(KoelnBoundaryFacade.class);

	private FeatureCollection featureCollection = new FeatureCollection();
	private String geoJson;
	private AskFor askFor;

	public KoelnBoundaryFacade(AskFor askFor) {
		this.askFor = askFor;
	}

	/**
	 * get GeoJson - String
	 * 
	 * @return
	 * @throws IOException
	 */
	@Override
	public String getGeoJson() throws IOException {

		// 1. get Data from DB
		long before = System.currentTimeMillis();
		List<PlanetOsmPolygon> pois;
		try {
			pois = askFor.setConnection(ConnectionFactory.getConnection())
					.getPois();
			// 2. User Dao for converting to Bo
			PlanetOsmPolygonDao planetOsmPolygonDao = new PlanetOsmPolygonDao(
					pois);
			// 3. change to feature List
			PlanetOsmFeature geojsonJacksonOsmFeature = new GeojsonJacksonOsmFeature(
					planetOsmPolygonDao.getPlanetOsmBoList());
			List<Feature> featureList = geojsonJacksonOsmFeature
					.getFeatureList();
			// 4. Prepare FeatureCollection; other features can be added.
			for (Feature feature : featureList) {
				featureCollection.add(feature);
			}
			geoJson = new ObjectMapper().writeValueAsString(featureCollection);
		} catch (SQLException e) {
			logger.error(e);
		} catch (JsonProcessingException e) {
			logger.error(e);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long after = System.currentTimeMillis();
		logger.debug("time needed: "
				+ new SimpleDateFormat("mm:ss.SSS").format(new Date(after
						- before)));

		return geoJson;
	}
}
