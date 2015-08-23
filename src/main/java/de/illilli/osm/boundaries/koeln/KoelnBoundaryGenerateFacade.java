package de.illilli.osm.boundaries.koeln;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class KoelnBoundaryGenerateFacade implements Facade {

	private static final Logger logger = Logger
			.getLogger(KoelnBoundaryGenerateFacade.class);
	private List<String> resultFileNameList = new ArrayList<String>();
	List<Integer> thresholdList = new ArrayList<Integer>();
	private List<String> levelList = new ArrayList<String>();

	private boolean fromResource = false;

	public KoelnBoundaryGenerateFacade(String level, Integer threshold)
			throws IOException {
		for (String getLevel : getLevel()) {
			for (Integer getThreshold : getThreshold()) {
				Facade facade = FacadeFactory.getFacade(fromResource, getLevel,
						getThreshold);
				String geoJson = facade.getGeoJson();
				File file = new File(
						ApplicationProperties.getProperty("generate.folder")
								+ getLevel + "_" + getThreshold + ".json");
				FileUtils.writeStringToFile(file, geoJson);
				resultFileNameList.add(file.getAbsolutePath());
				logger.info(file.getAbsolutePath());
			}
		}

	}

	List<String> getLevel() {
		levelList.add(LevelEnum.plz.name());
		levelList.add(LevelEnum.stadt.name());
		levelList.add(LevelEnum.stadtteile.name());
		levelList.add(LevelEnum.stadtbezirke.name());
		return levelList;
	}

	List<Integer> getThreshold() {
		thresholdList.add(1);
		thresholdList.add(10);
		thresholdList.add(100);
		thresholdList.add(200);
		thresholdList.add(500);
		thresholdList.add(1000);
		return thresholdList;
	}

	@Override
	public String getGeoJson() throws IOException {
		return new Gson().toJson(resultFileNameList);
	}

}
