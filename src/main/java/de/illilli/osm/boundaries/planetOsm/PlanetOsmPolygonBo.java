package de.illilli.osm.boundaries.planetOsm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.geojson.GeoJsonObject;
import org.geojson.GeometryCollection;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class PlanetOsmPolygonBo implements PlanetOsmBo {

	private String id;
	private Map<String, Object> properties;
	private String type;
	private GeometryCollection geometryCollection;

	public PlanetOsmPolygonBo(PlanetOsmPolygon planetOsmPolygon) {
		// set id
		id = Long.toString(planetOsmPolygon.getOsm_id());
		// set properties
		properties = new HashMap<String, Object>();
		properties.put("admin_level", planetOsmPolygon.getAdmin_level());
		properties.put("name", planetOsmPolygon.getName());
		properties.put("ref", planetOsmPolygon.getRef());
		// set geometry
		geometryCollection = new GeometryCollection();
		GeometrieDeserializerGeoJson geometrieDeserializer;
		try {
			geometrieDeserializer = new GeometrieDeserializerGeoJson(
					planetOsmPolygon.getWay());
			GeoJsonObject geometry = geometrieDeserializer.getGeometry();
			geometryCollection.add(geometry);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * return the fist geometry out of the geometryCollection.
	 */
	@Override
	public GeoJsonObject getGeometry() {
		return geometryCollection;
	}

	@Override
	public String getType() {
		return type;
	}

}
