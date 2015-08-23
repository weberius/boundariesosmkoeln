package de.illilli.osm.jdbc.koelnboundary;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import de.illilli.osm.boundaries.planetOsm.PlanetOsmPolygon;
import de.illilli.osm.jdbc.DbUtilsBeanListHandler;

public abstract class AskFor {

	String sql = "";
	private DbUtilsBeanListHandler<PlanetOsmPolygon> handler;
	private List<PlanetOsmPolygon> pois;
	private Integer threshold = 10;
	private Connection connection;

	public AskFor setThreshold(Integer threshold) {
		this.threshold = threshold;
		return this;
	}

	public AskFor setConnection(Connection connection) {
		this.connection = connection;
		return this;
	}

	public List<PlanetOsmPolygon> getPois() throws IOException, SQLException {
		initSql();

		if (connection == null) {
			throw new SQLException("connection not set.");
		}
		BeanListHandler<PlanetOsmPolygon> beanListHandler = new BeanListHandler<PlanetOsmPolygon>(
				PlanetOsmPolygon.class);
		handler = new DbUtilsBeanListHandler<PlanetOsmPolygon>(connection,
				beanListHandler, sql, new Integer(threshold));
		pois = handler.getList();
		connection.close();
		return pois;
	}

	abstract void initSql() throws IOException;
}
