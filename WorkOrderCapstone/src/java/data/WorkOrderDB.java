/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Admin;
import business.Receipt;
import business.User;
import business.WorkOrder;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ashbb
 */
public class WorkOrderDB {
    private static final Logger LOG = Logger.getLogger(WorkOrderDB.class.getName());

    public static void insertNewUser(User user) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO RMFall22.EndUser (UserName, Password, AccountAge, Email, FirstName, LastName, Phone, RoleID, Street, City, State, zip) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setDate(3, Date.valueOf(user.getAccountAge()));
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getFirstName());
            ps.setString(6, user.getLastName());
            ps.setString(7, user.getPhone());
            ps.setInt(8, user.getRoleID());
            ps.setString(9, user.getStreet());
            ps.setString(10, user.getCity());
            ps.setString(11, user.getState());
            ps.setInt(12, user.getZip());            
            
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** insert sql", e);
            throw e;
            
        } finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** insert null pointer??", e);
                throw e;
            }
        }
    }

    public static LinkedHashMap<Integer, User> selectAllUsers() throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM RMFall22.EndUser";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            
            User user = null;
            LinkedHashMap<Integer, User> users = new LinkedHashMap();

            while (rs.next()) {
                
                int CustomerID = rs.getInt("CustomerID");
                String UserName = rs.getString("UserName");
                String password = rs.getString("Password");
                LocalDate AccountAge = rs.getDate("AccountAge").toLocalDate();
                String FirstName = rs.getString("FirstName");
                String LastName = rs.getString("LastName");
                String Address = rs.getString("Street");
                String City = rs.getString("City");
                String State = rs.getString("State");
                int Zip = rs.getInt("zip");
                String Phone = rs.getString("Phone");
                String Email = rs.getString("Email");
                int Role = rs.getInt("RoleID");
                
                user = new User(CustomerID, UserName, password, AccountAge, Email, FirstName, LastName, Phone, Role, Address, City, State, Zip);
                users.put(CustomerID, user);
            }
            return users;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** select all sql", e);
            throw e;
            
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** select all null pointer??", e);
                throw e;
            }
        }
    }
    
     public static LinkedHashMap<Integer, Admin> selectAllAdmins() throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM RMFall22.Admin";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            
            Admin admin = null;
            LinkedHashMap<Integer, Admin> users = new LinkedHashMap();

            while (rs.next()) {
                
                int EmployeeID = rs.getInt("EmployeeID");
                String UserName = rs.getString("UserName");
                String password = rs.getString("Password");
                String FirstName = rs.getString("FirstName");
                String LastName = rs.getString("LastName");
                String Phone = rs.getString("Phone");
                int Role = rs.getInt("RoleID");
                LocalDate EmpHireDate = rs.getDate("EmpHireDate").toLocalDate();
                LocalDate EmpBirthDate = rs.getDate("EmpHireDate").toLocalDate();
                String EmpSSN = rs.getString("EmpSSN");
                
                admin = new Admin(EmployeeID, FirstName, LastName, UserName, password, Phone, Role, EmpHireDate, EmpBirthDate, EmpSSN);
                users.put(EmployeeID, admin);
            }
            return users;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** select all sql", e);
            throw e;
            
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** select all null pointer??", e);
                throw e;
            }
        }
    }
    
    public static LinkedHashMap<String, User> selectAllUsersByUserName() throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM RMFall22.EndUser";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            
            User user = null;
            LinkedHashMap<String, User> users = new LinkedHashMap();

            while (rs.next()) {
                
                int CustomerID = rs.getInt("CustomerID");
                String UserName = rs.getString("UserName");
                String password = rs.getString("Password");
                LocalDate AccountAge = rs.getDate("AccountAge").toLocalDate();
                String FirstName = rs.getString("FirstName");
                String LastName = rs.getString("LastName");
                String Address = rs.getString("Street");
                String City = rs.getString("City");
                String State = rs.getString("State");
                int Zip = rs.getInt("zip");
                String Phone = rs.getString("Phone");
                String Email = rs.getString("Email");
                int Role = rs.getInt("RoleID");
                
                user = new User(CustomerID, UserName, password, AccountAge, Email, FirstName, LastName, Phone, Role, Address, City, State, Zip);
                users.put(UserName, user);
            }
            return users;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** select all sql", e);
            throw e;
            
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** select all null pointer??", e);
                throw e;
            }
        }
    }
    
    public static void updateUser(User user) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "UPDATE RMFall22.EndUser SET Password = ?, AccountAge = ?, Email = ?, FirstName = ?, LastName = ?, Phone = ?, RoleID = ?, Street = ?, City = ?, State = ?, zip = ? "
                + "WHERE CustomerID = ?";
        
        try {
            ps = connection.prepareStatement(query);

            ps.setString(1, user.getPassword());
            ps.setDate(2, Date.valueOf(user.getAccountAge()));
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getPhone());
            ps.setInt(7, user.getRoleID());
            ps.setString(8, user.getStreet());
            ps.setString(9, user.getCity());
            ps.setString(10, user.getState());
            ps.setInt(11, user.getZip());  
            ps.setInt(12, user.getCustomerID());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** insert sql", e);
            throw e;
            
        } finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** insert null pointer??", e);
                throw e;
            }
        }
    }
    
    public static void deleteUsers(User user) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "DELETE FROM RMFall22.EndUser "
                + "WHERE CusomterID = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, user.getCustomerID());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** insert sql", e);
            throw e;
            
        } finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** insert null pointer??", e);
                throw e;
            }
        }
    }
    
    public static LinkedHashMap<String, String> getUsersEmail() throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT Email FROM RMFall22.EndUser";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            User user = null;
            LinkedHashMap<String, String> users = new LinkedHashMap();
            while (rs.next()) {
                String email = rs.getString("email");
                users.put(email, email);
            }
            return users;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** select all sql", e);
            throw e;
        } finally {
            try {
                rs.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** select all null pointer??", e);
                throw e;
            }
        }
    }
    
    public static LinkedHashMap<String, String> userLogin() throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT UserName, Password FROM RMFall22.EndUser";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            User user = null;
            LinkedHashMap<String, String> users = new LinkedHashMap();
            while (rs.next()) {
                String userName = rs.getString("UserName");
                String password = rs.getString("Password");
                users.put(userName, password);
            }
            return users;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** select all sql", e);
            throw e;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** select all null pointer??", e);
                throw e;
            }
        }
    }
    
    public static LinkedHashMap<String, String> adminLogin() throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT UserName, Password FROM RMFall22.Admin";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            User user = null;
            LinkedHashMap<String, String> users = new LinkedHashMap();
            while (rs.next()) {
                String userName = rs.getString("UserName");
                String password = rs.getString("Password");
                users.put(userName, password);
            }
            return users;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** select all sql", e);
            throw e;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** select all null pointer??", e);
                throw e;
            }
        }
    }
    
    public static Integer getCustomerID(String UserName) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT CustomerID FROM RMFall22.EndUser "
                      +"WHERE UserName = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, UserName);
            rs = ps.executeQuery();
            int ID = 0;
            while (rs.next()) {
                int CustomerID = Integer.parseInt(rs.getString("CustomerID"));
                ID = CustomerID;
            }
            return ID;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** select all sql", e);
            throw e;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** select all null pointer??", e);
                throw e;
            }
        }
    }
    
    public static Integer getAdminID(String UserName) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT EmployeeID FROM RMFall22.Admin "
                      +"WHERE UserName = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, UserName);
            rs = ps.executeQuery();
            int ID = 0;
            while (rs.next()) {
                int EmployeeID = Integer.parseInt(rs.getString("EmployeeID"));
                ID = EmployeeID;
            }
            return ID;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** select all sql", e);
            throw e;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** select all null pointer??", e);
                throw e;
            }
        }
    }
    
    public static LinkedHashMap<Integer, WorkOrder> selectAllUsersWorkOrders(int CustomerID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM RMFall22.WorkOrder "
                      +"WHERE CustomerID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, CustomerID);
            rs = ps.executeQuery();
            
            WorkOrder order = null;
            LinkedHashMap<Integer, WorkOrder> orders = new LinkedHashMap();

            while (rs.next()) {
                
                int WorkOrderID = rs.getInt("WorkOrderID");
                String WorkOrderDesc = rs.getString("WorkOrderDesc");
                Date WorkOrderStart = rs.getDate("WorkOrderStart");
                LocalDate WorkOrderSt = null;
                if (WorkOrderStart != null) {
                    WorkOrderSt = WorkOrderStart.toLocalDate();
                }
                Date WorkOrderEnd = rs.getDate("WorkOrderEnd");
                LocalDate WorkOrderE = null;
                if (WorkOrderEnd != null) {
                    WorkOrderE = WorkOrderEnd.toLocalDate();
                }
                Date WorkOrderEstEnd = rs.getDate("WorkOrderEstEnd");
                LocalDate WorkOrderEst = null;
                if(WorkOrderEstEnd != null){
                    WorkOrderEst = WorkOrderEstEnd.toLocalDate();
                    
                }
                Float EstPrice = rs.getFloat("EstPrice");
                Boolean Complete = rs.getBoolean("Complete");
                int AssignedAdmin = rs.getInt("AssignedAdmin");
                
                order = new WorkOrder(WorkOrderID, CustomerID, WorkOrderDesc, WorkOrderSt, WorkOrderEst, WorkOrderE, EstPrice, Complete, AssignedAdmin);
                orders.put(WorkOrderID, order);
            }
            return orders;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** select all sql", e);
            throw e;
            
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** select all null pointer??", e);
                throw e;
            }
        }
    }
    
    public static LinkedHashMap<Integer, WorkOrder> selectUnassignedWorkOrders(int EmployeeID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM RMFall22.WorkOrder "
                      +"WHERE AssignedAdmin = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, EmployeeID);
            rs = ps.executeQuery();
            
            WorkOrder order = null;
            LinkedHashMap<Integer, WorkOrder> orders = new LinkedHashMap();

            while (rs.next()) {
                
                int WorkOrderID = rs.getInt("WorkOrderID");
                int CustomerID = rs.getInt("CustomerID");
                String WorkOrderDesc = rs.getString("WorkOrderDesc");
                Date WorkOrderStart = rs.getDate("WorkOrderStart");
                LocalDate WorkOrderSt = null;
                if (WorkOrderStart != null) {
                    WorkOrderSt = WorkOrderStart.toLocalDate();
                }
                Date WorkOrderEnd = rs.getDate("WorkOrderEnd");
                LocalDate WorkOrderE = null;
                if (WorkOrderEnd != null) {
                    WorkOrderE = WorkOrderEnd.toLocalDate();
                }
                Date WorkOrderEstEnd = rs.getDate("WorkOrderEstEnd");
                LocalDate WorkOrderEst = null;
                if(WorkOrderEstEnd != null){
                    WorkOrderEst = WorkOrderEstEnd.toLocalDate();
                    
                }
                Float EstPrice = rs.getFloat("EstPrice");
                Boolean Complete = rs.getBoolean("Complete");
                int AssignedAdmin = rs.getInt("AssignedAdmin");
                
                order = new WorkOrder(WorkOrderID, CustomerID, WorkOrderDesc, WorkOrderSt, WorkOrderEst, WorkOrderE, EstPrice, Complete, AssignedAdmin);
                orders.put(WorkOrderID, order);
            }
            return orders;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** select all sql", e);
            throw e;
            
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** select all null pointer??", e);
                throw e;
            }
        }
    }
    
    public static LinkedHashMap<Integer, WorkOrder> selectAllAdminWorkOrders(int employeeID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM RMFall22.WorkOrder "
                      +"WHERE AssignedAdmin = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, employeeID);
            rs = ps.executeQuery();
            
            WorkOrder order = null;
            LinkedHashMap<Integer, WorkOrder> orders = new LinkedHashMap();

            while (rs.next()) {
                
                int WorkOrderID = rs.getInt("WorkOrderID");
                int CustomerID = rs.getInt("CustomerID");
                String WorkOrderDesc = rs.getString("WorkOrderDesc");
                Date WorkOrderStart = rs.getDate("WorkOrderStart");
                LocalDate WorkOrderSt = null;
                if (WorkOrderStart != null) {
                    WorkOrderSt = WorkOrderStart.toLocalDate();
                }
                Date WorkOrderEnd = rs.getDate("WorkOrderEnd");
                LocalDate WorkOrderE = null;
                if (WorkOrderEnd != null) {
                    WorkOrderE = WorkOrderEnd.toLocalDate();
                }
                Date WorkOrderEstEnd = rs.getDate("WorkOrderEstEnd");
                LocalDate WorkOrderEst = null;
                if(WorkOrderEstEnd != null){
                    WorkOrderEst = WorkOrderEstEnd.toLocalDate();
                    
                }
                Float EstPrice = rs.getFloat("EstPrice");
                Boolean Complete = rs.getBoolean("Complete");
                int AssignedAdmin = rs.getInt("AssignedAdmin");
                
                order = new WorkOrder(WorkOrderID, CustomerID, WorkOrderDesc, WorkOrderSt, WorkOrderEst, WorkOrderE, EstPrice, Complete, AssignedAdmin);
                orders.put(WorkOrderID, order);
            }
            return orders;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** select all sql", e);
            throw e;
            
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** select all null pointer??", e);
                throw e;
            }
        }
    }
    
    public static void createNewOrder(WorkOrder order) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO RMFall22.WorkOrder (CustomerID, WorkOrderDesc, WorkOrderStart, WorkOrderEnd, WorkOrderEstEnd, EstPrice, Complete, AssignedAdmin) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, order.getCustomerID());
            ps.setString(2, order.getWorkOrderDesc());
            ps.setDate(3, Date.valueOf(order.getWorkOrderStart()));
            ps.setDate(4, null);
            ps.setDate(5, null);
            ps.setFloat(6, order.getEstPrice());
            ps.setBoolean(7, order.getComplete());
            ps.setInt(8, order.getAssignedAdmin());           
            
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** insert sql", e);
            throw e;
            
        } finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** insert null pointer??", e);
                throw e;
            }
        }
    }
    
    public static LinkedHashMap<Integer, WorkOrder> selectNewWorkOrder(int CustomerID, LocalDate Start) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM RMFall22.WorkOrder "
                      +"WHERE CustomerID = ? AND WorkOrderStart = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, CustomerID);
            ps.setDate(2, Date.valueOf(Start));
            rs = ps.executeQuery();
            
            WorkOrder order = null;
            LinkedHashMap<Integer, WorkOrder> orders = new LinkedHashMap();

            while (rs.next()) {
                
                int WorkOrderID = rs.getInt("WorkOrderID");
                String WorkOrderDesc = rs.getString("WorkOrderDesc");
                Date WorkOrderStart = rs.getDate("WorkOrderStart");
                LocalDate WorkOrderSt = null;
                if (WorkOrderStart != null) {
                    WorkOrderSt = WorkOrderStart.toLocalDate();
                }
                Date WorkOrderEnd = rs.getDate("WorkOrderEnd");
                LocalDate WorkOrderE = null;
                if (WorkOrderEnd != null) {
                    WorkOrderE = WorkOrderEnd.toLocalDate();
                }
                Date WorkOrderEstEnd = rs.getDate("WorkOrderEstEnd");
                LocalDate WorkOrderEst = null;
                if(WorkOrderEstEnd != null){
                    WorkOrderEst = WorkOrderEstEnd.toLocalDate();
                    
                }
                Float EstPrice = rs.getFloat("EstPrice");
                Boolean Complete = rs.getBoolean("Complete");
                int AssignedAdmin = rs.getInt("AssignedAdmin");
                
                order = new WorkOrder(WorkOrderID, CustomerID, WorkOrderDesc, WorkOrderSt, WorkOrderEst, WorkOrderE, EstPrice, Complete, AssignedAdmin);
                orders.put(CustomerID, order);
            }
            return orders;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** select all sql", e);
            throw e;
            
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** select all null pointer??", e);
                throw e;
            }
        }
    }
    
    public static WorkOrder selectOneWorkOrder(int OrderID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM RMFall22.WorkOrder "
                      +"WHERE WorkOrderID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, OrderID);
            rs = ps.executeQuery();
            
            WorkOrder order = null;
            LinkedHashMap<Integer, WorkOrder> orders = new LinkedHashMap();

            while (rs.next()) {
                
                int WorkOrderID = rs.getInt("WorkOrderID");
                int CustomerID = rs.getInt("CustomerID");
                String WorkOrderDesc = rs.getString("WorkOrderDesc");
                Date WorkOrderStart = rs.getDate("WorkOrderStart");
                LocalDate WorkOrderSt = null;
                if (WorkOrderStart != null) {
                    WorkOrderSt = WorkOrderStart.toLocalDate();
                }
                Date WorkOrderEnd = rs.getDate("WorkOrderEnd");
                LocalDate WorkOrderE = null;
                if (WorkOrderEnd != null) {
                    WorkOrderE = WorkOrderEnd.toLocalDate();
                }
                Date WorkOrderEstEnd = rs.getDate("WorkOrderEstEnd");
                LocalDate WorkOrderEst = null;
                if(WorkOrderEstEnd != null){
                    WorkOrderEst = WorkOrderEstEnd.toLocalDate();
                    
                }
                Float EstPrice = rs.getFloat("EstPrice");
                Boolean Complete = rs.getBoolean("Complete");
                int AssignedAdmin = rs.getInt("AssignedAdmin");
                
                order = new WorkOrder(WorkOrderID, CustomerID, WorkOrderDesc, WorkOrderSt, WorkOrderEst, WorkOrderE, EstPrice, Complete, AssignedAdmin);
            }
            return order;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** select all sql", e);
            throw e;
            
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** select all null pointer??", e);
                throw e;
            }
        }
    }
    
    public static void cancelOrder(int OrderID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "DELETE FROM RMFall22.WorkOrder "
                + "WHERE WorkOrderID = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, OrderID);
            
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** insert sql", e);
            throw e;
            
        } finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** insert null pointer??", e);
                throw e;
            }
        }
    }
    
    public static void updateOrder(String Desc, int OrderID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "UPDATE RMFall22.WorkOrder SET WorkOrderDesc = ? "
                + "WHERE WorkOrderID = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, Desc);
            ps.setInt(2, OrderID);
            
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** insert sql", e);
            throw e;
            
        } finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** insert null pointer??", e);
                throw e;
            }
        }
    }
    
    public static void adminAssign(int EmployeeID, int OrderID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "UPDATE RMFall22.WorkOrder SET AssignedAdmin = ? "
                + "WHERE WorkOrderID = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, EmployeeID);
            ps.setInt(2, OrderID);
            
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** insert sql", e);
            throw e;
            
        } finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** insert null pointer??", e);
                throw e;
            }
        }
    }
    
    public static void adminComplete(int OrderID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "UPDATE RMFall22.WorkOrder SET Complete = 1, WorkOrderEnd = ? "
                + "WHERE WorkOrderID = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, OrderID);
            
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** insert sql", e);
            throw e;
            
        } finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** insert null pointer??", e);
                throw e;
            }
        }
    }
    
    public static void adminSubmitEdit(Float Price, LocalDate EstEndDate, int OrderID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "UPDATE RMFall22.WorkOrder SET EstPrice = ?, WorkOrderEstEnd = ? "
                + "WHERE WorkOrderID = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setFloat(1, Price);
            ps.setDate(2, Date.valueOf(EstEndDate));
            ps.setInt(3, OrderID);
            
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** insert sql", e);
            throw e;
            
        } finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** insert null pointer??", e);
                throw e;
            }
        }
    }
}
