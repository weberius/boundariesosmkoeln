package de.illilli.osm.boundaries.koeln;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ApplicationPropertiesTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String expected = ".";
		String actual = ApplicationProperties.getProperty("generate.folder");
		Assert.assertEquals(expected, actual);
	}

}
