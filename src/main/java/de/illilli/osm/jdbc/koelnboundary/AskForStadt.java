package de.illilli.osm.jdbc.koelnboundary;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class AskForStadt extends AskFor {

	@Override
	void initSql() throws IOException {
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/askforstadt.sql");
		sql = IOUtils.toString(inputStream);
	}

}
