package de.illilli.osm.boundaries.koeln;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

	private static final String fileName = "/application.properties";
	private static Properties properies;

	public static String getProperty(String key) {
		if (properies == null) {
			properies = new Properties();
			InputStream inputStream = ApplicationProperties.class
					.getResourceAsStream(fileName);
			try {
				properies.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properies.getProperty(key);
	}

}
