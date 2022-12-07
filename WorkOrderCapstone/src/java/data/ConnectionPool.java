/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Ashbb
 */
public class ConnectionPool {
    private static ConnectionPool pool = null;
    private static DataSource dataSource = null;
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class.getName());
    

    private ConnectionPool() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/RMFall22");
            
        } catch (NamingException e) {
           LOG.log(Level.SEVERE, "*** failed on datasource lookup", e);
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
           LOG.log(Level.SEVERE, "*** failed on getting connection", e);
            return null;
        }
    }

    public void freeConnection(Connection c) {
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

