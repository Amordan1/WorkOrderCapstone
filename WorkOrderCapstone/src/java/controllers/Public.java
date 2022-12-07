/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import business.User;
import data.WorkOrderDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.catalina.realm.SecretKeyCredentialHandler;

/**
 *
 * @author fs148523
 */
public class Public extends HttpServlet {

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

        String action = request.getParameter("action");

        String url = "/index.jsp";
        String errorMessage = "";
        String message = "";
        
        if (action == null) {
            action = "home";
        }

        switch (action) {
            
            case "home": {
                url = "/login.jsp";
                break;
            }
            case "login": {
                url = "/login.jsp";
                break;
            }
            
            case "admin": {
                url = "/admin.jsp";
                break;
            }
            case "register": {
                url = "/index.jsp";
                break;
            }
            case "add": {

                String userName = request.getParameter("userName");
                String email = request.getParameter("email");
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
                HttpSession session = request.getSession();
                
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
                
                

                User user = new User();
               

                
                try {
                    LinkedHashMap<String, User> linkMap = new LinkedHashMap(WorkOrderDB.selectAllUsersByUserName());
                    if (linkMap.containsKey(userName)) {
                        request.setAttribute("userNameError", "This user name is already taken.");
                        error1 = "There was a problem.";
                    }
                } catch (SQLException ex) {
                    errorMessage = "There was a problem.";
                }

                if (userName.length() <= 4 || userName.length() >= 20) {
                    request.setAttribute("userNameError", "Your user name must be between 5 and 20 characters.");
                    error1 = "There was a problem.";
                }
                
                if ("".equals(userName)) {
                    request.setAttribute("userNameError", "Username must not be blank.");
                    error1 = "There was a problem.";
                } else { 
                    user.setUserName(userName);
                }
                
                if (error1.isEmpty()) {
                    request.setAttribute("userNameError", "");
                }
                
                if (email.length() < 5) {
                    request.setAttribute("emailError", "Your email address isn't long enough.");
                    error2 = "There was a problem.";
                } else {
                    if (email != null) {
                        Pattern pattern = Pattern.compile("^[A-Za-z0-9.]{0,}[@]{1,1}[A-Za-z0-9].{1,}[A-Za-z0-9]{1,}");
                        Matcher matcher = pattern.matcher(email);
                        Boolean isMatch = matcher.matches();
                        if(isMatch == true){

                        } else {
                            request.setAttribute("emailError", "Invalid email address. Must be in the format of 'ABC@123.org'.");
                            error2 = "There was a problem.";
                        }


                    } else {
                        request.setAttribute("emailError", "Invalid email address. Must contain an @ before a .");
                        error2 = "There was a problem.";
                    }
                }
                              
                
                try {
                    if (WorkOrderDB.getUsersEmail().containsKey(email)) {
                        request.setAttribute("emailError", "This email address is already in use.");
                        error2 = "There was a problem.";
                    }
                } catch (SQLException sq) {
                }
                
                if ("".equals(email)) {
                    request.setAttribute("emailError", "Email must not be blank.");
                    error2 = "There was a problem.";
                } else {
                    user.setEmail(email);
                }
                
                if (error2.isEmpty()) {
                    request.setAttribute("emailError", "");
                }
                
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
                    user.setPassword(hashedPassword);
                    try {
                        WorkOrderDB.insertNewUser(user);
                    } catch (SQLException e) {

                    }
                    user.setPassword(password);
                    message = "You have succesfully registered " + user.getUserName();
                }
            }
        }
        request.setAttribute("message", message);
        request.setAttribute("errorMessage", errorMessage);
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
