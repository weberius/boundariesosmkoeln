package de.illilli.osm.boundaries.planetOsm;

import java.util.Map;

import org.geojson.GeoJsonObject;

public interface PlanetOsmBo {

	String getId();

	Map<String, Object> getProperties();

	GeoJsonObject getGeometry();

	String getType();

}
