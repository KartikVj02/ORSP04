package com.ncs.utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ncs.exceptions.ApplicationException;

public final class JDBCDataSource {

	public static JDBCDataSource dataSource = null;
	
	public static ComboPooledDataSource cpds = null;

	private JDBCDataSource() {

	}

	public static JDBCDataSource getInstance() {

		if (dataSource == null) {

			ResourceBundle rs = ResourceBundle.getBundle("com.ncs.bundle.system");

			dataSource = new JDBCDataSource();

			dataSource.cpds = new ComboPooledDataSource();

			try {
				dataSource.cpds.setDriverClass(rs.getString("driver"));
				dataSource.cpds.setJdbcUrl(rs.getString("url"));
				dataSource.cpds.setUser(rs.getString("username"));
				dataSource.cpds.setPassword(rs.getString("password"));
				dataSource.cpds.setInitialPoolSize(DataUtility.getInt(rs.getString("initialPoolSize")));
				dataSource.cpds.setAcquireIncrement(DataUtility.getInt(rs.getString("acquireIncrement")));
				dataSource.cpds.setMaxPoolSize(DataUtility.getInt(rs.getString("maxPoolSize")));
				dataSource.cpds.setMinPoolSize(DataUtility.getInt(rs.getString("minPoolSize")));
				dataSource.cpds.setMaxIdleTime(DataUtility.getInt(rs.getString("timeout")));

			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}

		}

		return dataSource;

	}

	public static Connection getConnection() throws Exception {
		return getInstance().cpds.getConnection();
	}

	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void trnRollback(Connection connection) throws ApplicationException {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
				throw new ApplicationException(ex.toString());
			}
		}
	}

}
  