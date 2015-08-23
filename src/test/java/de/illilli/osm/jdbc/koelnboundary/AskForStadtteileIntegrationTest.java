package de.illilli.osm.jdbc.koelnboundary;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.illilli.osm.boundaries.planetOsm.PlanetOsmPolygon;

/**
 * <p>
 * <a href=
 * "https://blogs.oracle.com/randystuph/entry/injecting_jndi_datasources_for_junit"
 * >Injecting JNDI datasources for JUnit Tests outside of a container </a>
 * </p>
 * <p>
 * </p>
 */
@Ignore
public class AskForStadtteileIntegrationTest {

	Connection connection;

	@Before
	public void setUp() throws Exception {
		// TODO: define connection
	}

	@Test
	public void test() throws SQLException, IOException, NamingException {
		Integer simplifyLevel = 10;
		AskForStadtteile askFor = (AskForStadtteile) new AskForStadtteile()
				.setConnection(connection).setThreshold(simplifyLevel);
		List<PlanetOsmPolygon> planetOsmPolygonList = askFor.getPois();
		for (PlanetOsmPolygon polygon : planetOsmPolygonList) {
			System.out.println(polygon.getName());
		}

	}

}
