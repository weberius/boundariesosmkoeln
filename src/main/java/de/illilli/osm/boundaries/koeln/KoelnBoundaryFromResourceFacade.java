package de.illilli.osm.boundaries.koeln;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * Reads json Document from resource. If doesn't exist, returns empty String.
 */
public class KoelnBoundaryFromResourceFacade implements Facade {

	private static final Logger logger = Logger
			.getLogger(KoelnBoundaryFromResourceFacade.class);

	private String fileName;

	public KoelnBoundaryFromResourceFacade(String level,
			Integer threshold) {
		fileName = "/" + level + "_" + threshold + ".json";
	}

	@Override
	public String getGeoJson() throws IOException {
		String json = "";
		InputStream inputStream = this.getClass().getResourceAsStream(fileName);

		if (inputStream != null) {
			json = IOUtils.toString(inputStream, "UTF-8");
		} else {
			logger.warn("File doesn't exist: " + fileName);
		}

		return json;
	}
}
