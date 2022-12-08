/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import business.Admin;
import business.Receipt;
import business.WorkOrder;
import business.User;
import data.WorkOrderDB;
import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static jdk.incubator.vector.VectorOperators.LOG;
import org.apache.catalina.realm.SecretKeyCredentialHandler;

/**
 *
 * @author fs148523
 */
public class Private extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "/workOrders.jsp";
        String message = "";
        String action = request.getParameter("action");
        //set an action value if there is none, to avoid null
        if (action == null) {
            action = "none";
        }

        HttpSession session = request.getSession();

        //check session to see if a user is logged in
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        if (loggedInUser == null || loggedInUser.equals("")) {
            //user is NOT logged in, set up a message and take them back to the index
            message = "Please login";
            url = "/login.jsp";

            //If they aren't logged in, check to see if they are trying to login
            //we know that if the action matches the login form hidden action
            if (action.equals("attemptLogin")) {
                String userName = request.getParameter("userName");
                String hash = request.getParameter("password");

                SecretKeyCredentialHandler ch;
                String password = "";
                Boolean check = false;

                HashMap<String, String> credentials = new HashMap();

                try {
                    credentials = WorkOrderDB.userLogin();
                } catch (SQLException e) {

                }
                //credentials = UserDB
                String passFromMap = credentials.get(userName);

                try {
                    ch = new SecretKeyCredentialHandler();
                    ch.setAlgorithm("PBKDF2WithHmacSHA256");
                    ch.setKeyLength(256);
                    ch.setSaltLength(16);
                    ch.setIterations(4096);

                    check = ch.matches(hash, passFromMap);
                } catch (Exception e) {

                }
                //hard coded credential list / replace with call to DB to retrieve
                //password stored for user.

                if (passFromMap == null || !check) {
                    //INVALID LOGIN - set generic error message and take them to index
                    message = "Your password is incorrect";
                    url = "/login.jsp";
                } else {
                    //VALID LOGIN - set success message and take them to page for
                    //logged in users
                    session.setAttribute("loggedInUser", userName);
                    session.setAttribute("loginPassword", hash);
                    HashMap<Integer, WorkOrder> orders = new HashMap();

                    try 
                    {
                        Integer ID = WorkOrderDB.getCustomerID(userName);
                        session.setAttribute("customerID", ID);
                        orders = WorkOrderDB.selectAllUsersWorkOrders(ID);
                    } catch (SQLException sq) {
                        
                    }
                    request.setAttribute("items", orders);
                    message = "Login Sucesss";
                    url = "/workOrders.jsp";
                }
            }
            if (action.equals("adminLogin")) {
                String userName = request.getParameter("userName");
                String hash = request.getParameter("password");

                SecretKeyCredentialHandler ch;
                String password = "";
                Boolean check = false;

                HashMap<String, String> credentials = new HashMap();

                try {
                    credentials = WorkOrderDB.adminLogin();
                } catch (SQLException e) {

                }
                //credentials = UserDB
                String passFromMap = credentials.get(userName);

                try {
                    ch = new SecretKeyCredentialHandler();
                    ch.setAlgorithm("PBKDF2WithHmacSHA256");
                    ch.setKeyLength(256);
                    ch.setSaltLength(16);
                    ch.setIterations(4096);

                    check = ch.matches(hash, passFromMap);
                } catch (Exception e) {

                }
                //hard coded credential list / replace with call to DB to retrieve
                //password stored for user.

                if (passFromMap == null) {
                    //INVALID LOGIN - set generic error message and take them to index
                    message = "Your password is incorrect";
                    url = "/admin.jsp";
                } else if (passFromMap.contains(hash)) {
                    //VALID LOGIN - set success message and take them to page for
                    //logged in users
                    session.setAttribute("loggedInUser", userName);
                    session.setAttribute("loginPassword", hash);
                    HashMap<Integer, WorkOrder> orders = new HashMap();

                    try 
                    {
                        Integer ID = WorkOrderDB.getAdminID(userName);
                        session.setAttribute("adminID", ID);
                        orders = WorkOrderDB.selectAllAdminWorkOrders(ID);
                    } catch (SQLException sq) {
                        
                    }
                    request.setAttribute("items", orders);
                    message = "Login Sucesss";
                    url = "/adminWorkOrders.jsp";
                } else {
                    message = "Your password is incorrect";
                    url = "/admin.jsp";
                }
            }
        }
        //code for logged in only actions should happen here
        switch (action) {
            case "logout": {
                session.invalidate();
                url = "/login.jsp";
                break;
            }
            case "adminEdit": {
                if (loggedInUser == null || loggedInUser.equals("")) {
                    //user is NOT logged in, set up a message and take them back to the index
                    message = "Please login";
                    url = "/admin.jsp";
                } else {
                    
                    int idValue = -1; 
                    try {
                        idValue = Integer.parseInt(request.getParameter("idValue"));
                    } catch (Exception e) {

                    }
                    WorkOrder order = new WorkOrder();
                    try {
                        order = WorkOrderDB.selectOneWorkOrder(idValue);
                        if (order.getComplete() == false){
                            WorkOrderDB.selectOneWorkOrder(idValue);
                        }
                    } catch (Exception e) {
                    }
                    request.setAttribute("estEndDate", order.getWorkOrderEstEnd());
                    request.setAttribute("estPrice", order.getEstPrice());
                    request.setAttribute("idValue", idValue);
                    url = "/adminEditOrder.jsp";
                }
                break;
            }
            case "adminSubmitEdit": {
                LinkedHashMap<Integer, WorkOrder> orders = new LinkedHashMap();
                try {
                    Integer employeeID = (Integer) session.getAttribute("adminID");
                    orders = WorkOrderDB.selectAllAdminWorkOrders(employeeID);
                } catch (Exception e) {
                }
                String idValue = (String) request.getParameter("idValue");
                String estEndDate = (String) request.getParameter("estEndDate");
                String estPrice = (String) request.getParameter("estPrice");


                WorkOrder currentOrder = orders.get(Integer.valueOf(idValue));
                try {
                    WorkOrderDB.adminSubmitEdit(Float.parseFloat(estPrice), LocalDate.parse(estEndDate), currentOrder.getWorkOrderID());
                } catch (Exception e) {
                }

               try {
                    Integer employeeID = (Integer) session.getAttribute("adminID");
                    orders = WorkOrderDB.selectAllAdminWorkOrders(employeeID);
                } catch (Exception e) {
                }
                request.setAttribute("items", orders);
                url = "/adminWorkOrders.jsp";
                break;
            }
            
            case "adminComplete": {
                if (loggedInUser == null || loggedInUser.equals("")) {
                    //user is NOT logged in, set up a message and take them back to the index
                    message = "Please login";
                    url = "/admin.jsp";
                } else {
                    HashMap<Integer, WorkOrder> orders = new HashMap();
                    int idValue = Integer.parseInt(request.getParameter("idValue"));
                    Integer employeeID = (Integer) session.getAttribute("adminID");

                    try {
                        WorkOrderDB.adminComplete(idValue);
                        
                        orders = WorkOrderDB.selectAllAdminWorkOrders(employeeID);
                        request.setAttribute("items", orders);
                        
                    } catch (SQLException e) {
                        
                    }
                    url = "/adminWorkOrders.jsp";
                }
                break;
            }
            
            case "orders": {
                if (loggedInUser == null || loggedInUser.equals("")) {
                    //user is NOT logged in, set up a message and take them back to the index
                    message = "Please login";
                    url = "/login.jsp";
                } else {

                    HashMap<Integer, WorkOrder> orders = new HashMap();
                    String userName = (String) session.getAttribute("loggedInUser");

                    try 
                    {
                        Integer ID = WorkOrderDB.getAdminID(userName);
                        session.setAttribute("adminID", ID);
                        orders = WorkOrderDB.selectAllAdminWorkOrders(ID);
                    } catch (SQLException sq) {
                        
                    }
                    request.setAttribute("items", orders);
                    url = "/adminWorkOrders.jsp";
                }
                break;
            }
            
            case "display": {
                if (loggedInUser == null || loggedInUser.equals("")) {
                    //user is NOT logged in, set up a message and take them back to the index
                    message = "Please login";
                    url = "/login.jsp";
                } else {
                    HashMap<Integer, WorkOrder> orders = new HashMap();
                    Integer customerID = (Integer) session.getAttribute("customerID");

                    try {
                        orders = WorkOrderDB.selectAllUsersWorkOrders(customerID);
                        request.setAttribute("items", orders);
                        
                    } catch (SQLException e) {
                        
                    }
                    url = "/workOrders.jsp";
                }
                break;
            }
            case "new": {
                if (loggedInUser == null || loggedInUser.equals("")) {
                    //user is NOT logged in, set up a message and take them back to the index
                    message = "Please login";
                    url = "/login.jsp";
                } else {
                    url = "/newOrder.jsp";
                }
                break;
            }
            
            case "adminAll": {
                if (loggedInUser == null || loggedInUser.equals("")) {
                    //user is NOT logged in, set up a message and take them back to the index
                    message = "Please login";
                    url = "/admin.jsp";
                } else {
                    HashMap<Integer, WorkOrder> orders = new HashMap();

                    try {
                        orders = WorkOrderDB.selectUnassignedWorkOrders(0);
                        request.setAttribute("items", orders);
                        
                    } catch (SQLException e) {
                        
                    }
                    url = "/adminAll.jsp";
                }
                break;
            }
            
            case "adminAssign": {
                if (loggedInUser == null || loggedInUser.equals("")) {
                    //user is NOT logged in, set up a message and take them back to the index
                    message = "Please login";
                    url = "/admin.jsp";
                } else {
                    HashMap<Integer, WorkOrder> orders = new HashMap();
                    int idValue = Integer.parseInt(request.getParameter("idValue"));
                    Integer employeeID = (Integer) session.getAttribute("adminID");

                    try {
                        WorkOrderDB.adminAssign(employeeID, idValue);
                        
                        orders = WorkOrderDB.selectUnassignedWorkOrders(0);
                        request.setAttribute("items", orders);
                        
                    } catch (SQLException e) {
                        
                    }
                    url = "/adminAll.jsp";
                }
                break;
            }

            case "createOrder": {
                //get current time, new user's post

                LocalDate currentTime = LocalDate.now();
                String orderText = (String) request.getParameter("orderText");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                WorkOrder newOrder = new WorkOrder();
                int ID = (int) session.getAttribute("customerID");
                int characterLength = orderText.length();
                //if post is longer than 1024, split
                if (characterLength > 2000) {
                    message = "Your post is too long. Maximum character length is 2000 and you entered " + characterLength + " characters.";
                } else {
                    newOrder.setCustomerID(ID);
                    newOrder.setWorkOrderStart(currentTime);
                    newOrder.setWorkOrderDesc(orderText);
                    newOrder.setComplete(false);
                    newOrder.setWorkOrderEnd(null);
                    newOrder.setWorkOrderEstEnd(null);
                    newOrder.setAssignedAdmin(0);
                    newOrder.setEstPrice(0);
                    try {
                        WorkOrderDB.createNewOrder(newOrder);
                    } catch (Exception e) {
                        message = "SQL Error something wrong with your post.";
                    }
                    HashMap<Integer, WorkOrder> orders = new HashMap();
                    Integer customerID = (Integer) session.getAttribute("customerID");

                    try {
                        orders = WorkOrderDB.selectAllUsersWorkOrders(customerID);
                        request.setAttribute("items", orders);
                        
                    } catch (SQLException e) {
                        
                    }
                    action="display";
                }
                break;
            }
            case "editOrder": {
                if (loggedInUser == null || loggedInUser.equals("")) {
                    //user is NOT logged in, set up a message and take them back to the index
                    message = "Please login";
                    url = "/login.jsp";
                } else {
                    
                    int idValue = -1; 
                    try {
                        idValue = Integer.parseInt(request.getParameter("orderID"));
                    } catch (Exception e) {

                    }
                    WorkOrder order = new WorkOrder();
                    try {
                        order = WorkOrderDB.selectOneWorkOrder(idValue);
                        if (order.getComplete() == false){
                            WorkOrderDB.selectOneWorkOrder(idValue);
                        }
                    } catch (Exception e) {
                    }
                    request.setAttribute("orderText", order.getWorkOrderDesc());
                    request.setAttribute("idValue", idValue);
                    url = "/editOrder.jsp";
                }
                break;
            }
            case "submitEdit": {
                LinkedHashMap<Integer, WorkOrder> orders = new LinkedHashMap();
                try {
                    Integer customerID = (Integer) session.getAttribute("customerID");
                    orders = WorkOrderDB.selectAllUsersWorkOrders(customerID);
                } catch (Exception e) {
                }
                String idValue = (String) request.getParameter("idValue");
                String postText = (String) request.getParameter("orderText");

                WorkOrder currentOrder = orders.get(Integer.valueOf(idValue));
                try {
                    WorkOrderDB.updateOrder(postText, currentOrder.getWorkOrderID());
                } catch (Exception e) {
                }

                try {
                    Integer customerID = (Integer) session.getAttribute("customerID");
                    orders = WorkOrderDB.selectAllUsersWorkOrders(customerID);
                } catch (Exception e) {
                }
                request.setAttribute("items", orders);
                url = "/workOrders.jsp";
                break;
            }
            case "cancelOrder": {

                int idValue = -1;
                try {
                    idValue = Integer.parseInt(request.getParameter("idValue"));
                } catch (Exception e) {
                    
                }
                WorkOrder order = new WorkOrder();
                try {
                    order = WorkOrderDB.selectOneWorkOrder(idValue);
                    if (order.getComplete() == false){
                        WorkOrderDB.cancelOrder(idValue);
                    }
                } catch (Exception e) {
                }
                LinkedHashMap<Integer, WorkOrder> orders = new LinkedHashMap();
                try {
                    Integer customerID = (Integer) session.getAttribute("customerID");
                    orders = WorkOrderDB.selectAllUsersWorkOrders(customerID);
                } catch (Exception e) {
                    
                }
                request.setAttribute("items", orders);
                break;

            }
            case "profile": {

                if (loggedInUser == null || loggedInUser.equals("")) {
                    //user is NOT logged in, set up a message and take them back to the index
                    url = "/login.jsp";
                    message = "Please login";
                } else {
                    url = "/profile.jsp";
                    LinkedHashMap<Integer, User> users = new LinkedHashMap();
                    try {
                        users = WorkOrderDB.selectAllUsers();
                    } catch (Exception e) {
                    }
                    if (users != null) {
                        Integer customerID = (Integer) session.getAttribute("customerID");
                        String password = (String) session.getAttribute("loginPassword");
                        User currentUser = users.get(customerID);
                        currentUser.setPassword(password);

                        request.setAttribute("user", currentUser);
                    }
                }
                break;
            }
            
            case "adminProfile": {

                if (loggedInUser == null || loggedInUser.equals("")) {
                    //user is NOT logged in, set up a message and take them back to the index
                    url = "/admin.jsp";
                    message = "Please login";
                } else {
                    url = "/adminProfile.jsp";
                    LinkedHashMap<Integer, Admin> admins = new LinkedHashMap();
                    try {
                        admins = WorkOrderDB.selectAllAdmins();
                    } catch (Exception e) {
                    }
                    if (admins != null) {
                        Integer employeeID = (Integer) session.getAttribute("adminID");
                        String password = (String) session.getAttribute("loginPassword");
                        Admin currentUser = admins.get(employeeID);
                        currentUser.setPassword(password);

                        request.setAttribute("user", currentUser);
                    }
                }
                break;
            }

            case "editProfile": {
                if (loggedInUser == null || loggedInUser.equals("")) {
                    //user is NOT logged in, set up a message and take them back to the index
                    message = "Please login";
                    url = "login.jsp";
                } else {
                    //user is logged in
                    String errorMessage = "";
                    Integer customerID = (Integer) session.getAttribute("customerID");
                    HashMap<Integer, User> users = new HashMap();
                    try {
                        users = WorkOrderDB.selectAllUsers();
                    } catch (SQLException e) {
                    }
                    //create a list of users to pull logged in user from
                    if (users != null) {
                        
                    String password = request.getParameter("password");
                    String firstName = request.getParameter("firstName");
                    String lastName = request.getParameter("lastName");
                    String phone = request.getParameter("phone");
                    String street = request.getParameter("street");
                    String city = request.getParameter("city");
                    String state = request.getParameter("state");
                    String zip = request.getParameter("zip");
                    String hashedPassword = "";
                    SecretKeyCredentialHandler ch;

                    String error1 = "";
                    String error2 = "";
                    String error3 = "";
                    String error4 = "";
                    String error5 = "";
                    String error6 = "";
                    String error7 = "";
                    String error8 = "";
                    String error9 = "";
                    String error10 = "";

                    User user = users.get(customerID);

                    if (password.length() <= 8) {
                        request.setAttribute("passwordError", "The password isn't long enough. <br>It must be over 8 characters.");
                        error3 = "There was a problem.";
                    } else {
                        try {
                        ch = new SecretKeyCredentialHandler();
                        ch.setAlgorithm("PBKDF2WithHmacSHA256");
                        ch.setKeyLength(256);
                        ch.setSaltLength(16);
                        ch.setIterations(4096);

                        hashedPassword = ch.mutate(password);
                        } catch (Exception e){

                        }
                    }

                    if ("".equals(password)) {
                        request.setAttribute("passwordError", "Password must not be blank.");
                        error3 = "There was a problem.";
                    } else {
                        user.setPassword(password);
                    }

                    if (error3.isEmpty()) {
                        request.setAttribute("passwordError", "");
                    }

                    if ("".equals(firstName)){
                        request.setAttribute("firstNameError", "First Name must not be blank.");
                        error4 = "There was a problem.";
                    } else {
                        user.setFirstName(firstName);
                    }

                    if (error4.isEmpty()) {
                        request.setAttribute("firstNameError", "");
                    }

                    if ("".equals(lastName)){
                        request.setAttribute("lastNameError", "Last Name must not be blank.");
                        error5 = "There was a problem.";
                    } else {
                        user.setLastName(lastName);
                    }

                    if (error5.isEmpty()) {
                        request.setAttribute("lastNameError", "");
                    }

                    if ("".equals(phone)){
                        request.setAttribute("phoneError", "Phone must not be blank.");
                        error6 = "There was a problem.";
                    } else {
                        user.setPhone(phone);
                        Pattern pattern = Pattern.compile("^[0-9]{0,1}[ ]{0,1}[0-9]{3,3}[-][0-9]{3,3}[-][0-9]{4,4}");
                        Matcher matcher = pattern.matcher(phone);
                            Boolean isMatch = matcher.matches();
                            if(isMatch == true){

                            } else {
                                error6 = "There was a problem.";
                                request.setAttribute("phoneError", "Phone number be in the format of 123-456-7890 or 1 234-567-8910");
                            }
                    }

                    if (error6.isEmpty()) {
                        request.setAttribute("phoneError", "");
                    }

                    if ("".equals(street)){
                        request.setAttribute("streetError", "Street must not be blank.");
                        error7 = "There was a problem.";
                    } else {
                        user.setStreet(street);
                    }

                    if (error7.isEmpty()) {
                        request.setAttribute("streetError", "");
                    }

                    if ("".equals(city)){
                        request.setAttribute("cityError", "City must not be blank.");
                        error8 = "There was a problem.";
                    } else {
                        user.setCity(city);
                    }

                    if (error8.isEmpty()) {
                        request.setAttribute("cityError", "");
                    }

                    if ("".equals(state)){
                        request.setAttribute("stateError", "State must not be blank.");
                        error9 = "There was a problem.";
                    } else {
                        user.setState(state);
                        Pattern pattern = Pattern.compile("^[A-Z]{2,2}");
                        Matcher matcher = pattern.matcher(state);
                            Boolean isMatch = matcher.matches();
                            if(isMatch == true){

                            } else {
                                error9 = "There was a problem.";                            
                                request.setAttribute("stateError", "State must be in its abrreviated form, capitalized.");
                            }
                    }

                    if (error9.isEmpty()) {
                        request.setAttribute("stateError", "");
                    }

                    if ("".equals(zip)){
                        request.setAttribute("zipError", "Zip must not be blank.");
                        error10 = "There was a problem.";
                    } else {
                        user.setZip(Integer.parseInt(zip));
                        Pattern pattern = Pattern.compile("^[0-9]{5,5}");
                        Matcher matcher = pattern.matcher(zip);
                            Boolean isMatch = matcher.matches();
                            if(isMatch == true){

                            } else {
                                request.setAttribute("zipError", "Zip must be 5 digits.");
                                error10 = "There was a problem.";
                            }
                    } 

                    if (error10.isEmpty()) {
                        request.setAttribute("zipError", "");
                    }

                    errorMessage = error1+error2+error3+error4+error5+error6+error7+error8+error9+error10;

                    request.setAttribute("user", user);
                    if (errorMessage.isEmpty()) {
                        // add the person in the users
                        user.setAccountAge(LocalDate.now());       
                        user.setRoleID(1);
                        request.setAttribute("loginPassword", user.getPassword());
                        user.setPassword(hashedPassword);
                        try {
                            WorkOrderDB.updateUser(user);
                        } catch (SQLException e) {

                        }
                        url="/profile.jsp";
                        user.setPassword(password);
                        message = "You have succesfully registered " + user.getUserName();
                    }
                }
            }
        break;
        }

    }
        //regardless of what happens put the message in the request and forward
        // to url
        request.setAttribute("message", message);

        getServletContext().getRequestDispatcher(url).forward(request, response);
}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
