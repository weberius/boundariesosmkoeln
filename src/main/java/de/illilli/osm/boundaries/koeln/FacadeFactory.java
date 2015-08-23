package de.illilli.osm.boundaries.koeln;

import java.io.IOException;

import de.illilli.osm.jdbc.koelnboundary.AskForPlz;
import de.illilli.osm.jdbc.koelnboundary.AskForStadt;
import de.illilli.osm.jdbc.koelnboundary.AskForStadtbezirke;
import de.illilli.osm.jdbc.koelnboundary.AskForStadtteile;

public class FacadeFactory {

	public static Facade getFacade(boolean fromResource,
			String level, Integer threshold)
			throws IOException {
		Facade facade = null;
		if (fromResource) {
			facade = new KoelnBoundaryFromResourceFacade(level,
					threshold);
		} else {
			if (LevelEnum.stadtteile.name().equals(level)) {
				facade = new KoelnBoundaryFacade(
						new AskForStadtteile().setThreshold(threshold));
			} else if (LevelEnum.stadtbezirke.name().equals(
					level)) {
				facade = new KoelnBoundaryFacade(
						new AskForStadtbezirke()
								.setThreshold(threshold));
			} else if (LevelEnum.stadt.name().equals(level)) {
				facade = new KoelnBoundaryFacade(
						new AskForStadt().setThreshold(threshold));
			} else if (LevelEnum.plz.name().equals(level)) {
				facade = new KoelnBoundaryFacade(
						new AskForPlz().setThreshold(threshold));
			}
		}
		return facade;
	}
}
