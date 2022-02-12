package in.co.rays.project_4.util;

import in.co.rays.project_4.exception.ApplicationException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBC DataSource is a Data Connection Pool
 *
 * @author Danish Khan
 * @version 1.0
 * @Copyright (c) SunilOS
 *
 */

/**
 * @author Danish
 *
 */
public final class JDBCDataSource {
	/**
	 * JDBC Database connection pool ( DCP )
	 */
	private static JDBCDataSource datasource;

	private JDBCDataSource() {
	}

	private ComboPooledDataSource cpds = null;

	/**
	 * Create instance of Connection Pool
	 *
	 * @return
	 */
	public static JDBCDataSource getInstance() {
		System.out.println("JDBCDataSource.getInstance() line 41");
		if (datasource == null) {

			ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.project_4.bundle.System");

			datasource = new JDBCDataSource();
			datasource.cpds = new ComboPooledDataSource();
			try {
				datasource.cpds.setDriverClass(rb.getString("driver"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			datasource.cpds.setJdbcUrl(rb.getString("url"));
			datasource.cpds.setUser(rb.getString("username"));
			datasource.cpds.setPassword(rb.getString("password"));
			datasource.cpds.setInitialPoolSize(new Integer((String) rb.getString("initialPoolSize")));
			datasource.cpds.setAcquireIncrement(new Integer((String) rb.getString("acquireIncrement")));
			datasource.cpds.setMaxPoolSize(new Integer((String) rb.getString("maxPoolSize")));
			datasource.cpds.setMaxIdleTime(DataUtility.getInt(rb.getString("timeout")));
			datasource.cpds.setMinPoolSize(new Integer((String) rb.getString("minPoolSize")));

		}
		return datasource;
	}

	/**
	 * Gets the connection from ComboPooledDataSource
	 *
	 * @return connection
	 */
	public static Connection getConnection() throws Exception {
		System.out.println("JDBCDataSource.getConnection() line 72");
		return getInstance().cpds.getConnection();
	}

	/**
	 * Closes a connection
	 *
	 * @param connection
	 * @throws Exception
	 */
	public static void closeConnection(Connection connection) {
		System.out.println("JDBCDataSource.closeConnection(connection) line 83");
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
			}
		}
	}

	public static void trnRollback(Connection connection) throws ApplicationException {
		System.out.println("JDBCDataSource.trnRollback(connection) line 93");
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
				throw new ApplicationException(ex.toString());
			}
		}
	}
}
