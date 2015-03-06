package com.ncs.util;

import java.sql.Connection;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCDataSource2 {

    private static JDBCDataSource2 datasource;
    
    private JDBCDataSource2() {
    }
 
    private ComboPooledDataSource cpds = null;
 
    public static JDBCDataSource2 getInstance() {
        if (datasource == null) {
 
            ResourceBundle rb = ResourceBundle
                    .getBundle("com.ncs.bundle.system");
 
            datasource = new JDBCDataSource2();
            datasource.cpds = new ComboPooledDataSource();
            try {
                datasource.cpds.setDriverClass(rb.getString("driver"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            datasource.cpds.setJdbcUrl(rb.getString("kip.url"));
            datasource.cpds.setUser(rb.getString("username"));
            datasource.cpds.setPassword(rb.getString("password"));
            datasource.cpds.setInitialPoolSize(new Integer((String) rb
                    .getString("initialPoolSize")));
            datasource.cpds.setAcquireIncrement(new Integer((String) rb
                    .getString("acquireIncrement")));
            datasource.cpds.setMaxPoolSize(new Integer((String) rb
                    .getString("maxPoolSize")));
            datasource.cpds.setMaxIdleTime(DataUtility.getInt(rb.getString("timeout")));
            datasource.cpds.setMinPoolSize(new Integer((String) rb
                    .getString("minPoolSize")));
 
        }
        return datasource;
    }
    

 
    /**
     * Gets the connection from ComboPooledDataSource
     * 
     * @return connection
     */
    public static Connection getConnection() throws Exception {
        return getInstance().cpds.getConnection();
    }
    
 
 
    /**
     * Closes a connection
     * 
     * @param connection
     * @throws Exception
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }
}
