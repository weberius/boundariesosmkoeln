package de.illilli.osm.boundaries.planetOsm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.log4j.Logger;
import org.geojson.GeoJsonObject;
import org.geojson.LineString;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.geojson.Polygon;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Diese Klasse verwandelt einen String aus <code> ST_AsText(ST_Transform(way, 4326))</code> in eine Liste aus LngLatAlt - Objekten.
 * 
 * @see LngLatAlt
 */
public class GeometrieDeserializerGeoJson {

	private static final Logger logger = Logger.getLogger(GeometrieDeserializerGeoJson.class);

	public static final String POINT = "Point";
	public static final String MULTIPOINT = "MultiPoint";
	public static final String POLYGON = "Polygon";
	public static final String MULTIPOLYGON = "MultiPolygon";
	public static final String LINESTRING = "LineString";
	public static final String MULTILINESTRING = "MultiLineString";

	private GeoJsonObject geometry;
	private String type;

	public GeometrieDeserializerGeoJson(String way) throws JsonParseException, JsonMappingException, IOException {
		// logger.debug(way);
		InputStream inputStream = new ByteArrayInputStream(way.getBytes(StandardCharsets.UTF_8));
		geometry = new ObjectMapper().readValue(inputStream, GeoJsonObject.class);
		if (geometry instanceof LineString) {
			setType(LINESTRING);
		} else if (geometry instanceof Polygon) {
			setType(POLYGON);
		} else if (geometry instanceof Point) {
			setType(POINT);
		}
	}

	public GeoJsonObject getGeometry() {
		return geometry;
	}

	public String getType() {
		return type;
	}

	private void setType(String type) {
		this.type = type;
	}

}
