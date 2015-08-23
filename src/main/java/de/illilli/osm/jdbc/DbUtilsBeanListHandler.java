package de.illilli.osm.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class DbUtilsBeanListHandler<T> {

	private List<T> pois = null;

	public DbUtilsBeanListHandler(Connection conn,
			BeanListHandler<T> beanListHandler, String sql, Object... params)
			throws SQLException {
		QueryRunner query = new QueryRunner();

		pois = query.query(conn, sql, beanListHandler, params);
	}

	public List<T> getList() {
		return pois;
	}
}
