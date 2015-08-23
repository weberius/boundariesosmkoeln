package de.illilli.osm.jdbc.koelnboundary;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class AskForStadtbezirke extends AskFor {

	@Override
	void initSql() throws IOException {
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/askforstadtbezirke.sql");
		sql = IOUtils.toString(inputStream);
	}

}
