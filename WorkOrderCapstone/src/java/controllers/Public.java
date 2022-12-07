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

                User user = new User();
               

                if ("".equals(userName)) {
                    errorMessage += "Username must not be blank. <br>";
                }
                try {
                    LinkedHashMap<String, User> linkMap = new LinkedHashMap(WorkOrderDB.selectAllUsers());
                    if (linkMap.containsKey(userName)) {
                        errorMessage += "This user name is already taken.<br> ";
                    }
                } catch (SQLException ex) {
                    errorMessage += "There was a problem.";
                }

                if (userName.length() <= 4 && userName.length() >= 20) {
                    errorMessage += "Your user name must be between 4 and 20 characters.<br> ";
                }
                if ("".equals(email)) {
                    errorMessage += "Email must not be blank. <br>";
                }
                if (email.length() < 5) {
                    errorMessage += "Your email address isn't long enough.<br> ";
                }
                if (email != null) {
                    Pattern pattern = Pattern.compile("^[A-Za-z0-9.]{0,}[@]{1,1}[A-Za-z0-9].{1,}[A-Za-z0-9]{1,}");
                    Matcher matcher = pattern.matcher(email);
                    Boolean isMatch = matcher.matches();
                    if(isMatch == true){
                        
                    } else {
                        errorMessage += "Invalid email address. Must be in the format of 'ABC@123.org'.";
                    }

                    
                } else {
                    errorMessage += "Invalid email address. Must contain an @ before a .";
                }
                try {
                    if (WorkOrderDB.getUsersEmail().containsKey(email)) {
                        errorMessage += "This email address is already in use. <br>";
                    }
                } catch (SQLException sq) {
                }
                if ("".equals(password)) {
                    errorMessage += "Password must not be blank. <br>";
                }
                if (password.length() <= 8) {
                    errorMessage += "The password isn't long enough. <br>It must be over 8 characters. <br>";
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

                    user.setUserName(userName);
                    user.setPassword(hashedPassword);
                    user.setEmail(email);
                    user.setAccountAge(LocalDate.now());
                    user.setRoleID(1);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setPhone(phone);
                    user.setStreet(street);
                    user.setCity(city);
                    user.setState(state);
                    user.setZip(Integer.parseInt(zip));
                request.setAttribute("user", user);
                if (errorMessage.isEmpty()) {
                    // add the person in the users

                    try {
                        WorkOrderDB.insertNewUser(user);
                    } catch (SQLException e) {

                    }
                    user.setPassword(password);
                    errorMessage = "You have succesfully registered.  " + user.getUserName();
                }
            }
        }
        
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
