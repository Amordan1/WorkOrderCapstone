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
        }
        //code for logged in only actions should happen here
        switch (action) {
            case "logout": {
                session.invalidate();
                url = "/login.jsp";
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
                    LinkedHashMap<Integer, WorkOrder> orderMessage = new LinkedHashMap();
                    try {
                        orderMessage = WorkOrderDB.selectNewWorkOrder(ID, currentTime);
                    } catch (Exception e) {
                        
                    }
                    //create a list of users to pull logged in user from
                    if (orderMessage != null && loggedInUser != null) {
                        WorkOrder order = orderMessage.get(ID);
                        int orderID = order.getWorkOrderID();
                        message = "Order #" + orderID + " was successfully added.";
                    }
                }
                break;

            }
//            case "submitEdit": {
//                LinkedHashMap<Integer, Post> posts = new LinkedHashMap();
//                try {
//                    posts = PostDB.selectAllCurrentUser(loggedInUser);
//
//                } catch (Exception e) {
//                }
//                String idValue = (String) request.getParameter("idValue");
//                String postText = (String) request.getParameter("postText");
//
//                Post currentPost = posts.get(Integer.valueOf(idValue));
//                try {
//                    PostDB.postUpdate(currentPost.getId(), postText);
//                } catch (Exception e) {
//                }
//                try {
//                    posts = PostDB.selectAllCurrentUser(loggedInUser);
//
//                } catch (Exception e) {
//                }
//                request.setAttribute("posts", posts);
//                url = "/profile.jsp";
//                break;
//            }
//            case "editOrder": {
//                url = "/editPost.jsp";
//
//                LinkedHashMap<Integer, Post> posts = new LinkedHashMap();
//                try {
//                    posts = PostDB.selectAllCurrentUser(loggedInUser);
//
//                } catch (Exception e) {
//                }
//                request.setAttribute("posts", posts);
//                String idValue = request.getParameter("idValue");
//
//                Post currentPost = posts.get(Integer.valueOf(idValue));
//                request.setAttribute("idValue", idValue);
//                request.setAttribute("postText", currentPost.getPost());
//
//                break;
//
//            }
//
//            case "deleteOrder": {
//
//                String idValue = request.getParameter("idValue");
//
//                try {
//                    PostDB.postDelete(idValue);
//                } catch (Exception e) {
//                }
//                LinkedHashMap<Integer, Post> posts = new LinkedHashMap();
//                try {
//                    posts = PostDB.selectAllCurrentUser(loggedInUser);
//
//                } catch (Exception e) {
//                }
//                request.setAttribute("posts", posts);
//                break;
//
//            }
//            case "orderReply": {
//                LocalDateTime currentTime = LocalDateTime.now();
//                String commentText = (String) request.getParameter("commentText");
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                int commentID = Integer.valueOf(request.getParameter("commentID"));
//
//                Comment newComment = new Comment();
//
//                int characterLength = commentText.length();
//                //if cmt is longer than 140, split
//                if (characterLength > 140) {
//                    message = "Your comment is too long. Maximum character length is 140 and you entered " + characterLength + " characters.";
//                } else {
//                    newComment.setTime(currentTime.format(formatter));
//                    newComment.setComment(commentText);
//                    newComment.setUsername(loggedInUser);
//                    newComment.setPostID(commentID);
//                    try {
//                        CommentDB.insert(newComment);
//                    } catch (Exception e) {
//                        message = "SQL Error something wrong with your post.";
//                    }
//                    LinkedHashMap<Integer, Comment> comments = new LinkedHashMap();
//                    try {
//                        comments = CommentDB.selectAllCurrentUser();
//
//                    } catch (Exception e) {
//                    }
//                    HashMap<String, User> users = new HashMap();
//                    try {
//                        users = UserDB.selectAll();
//                    } catch (SQLException e) {
//                    }
//                    //create a list of users to pull logged in user from
//                    if (users != null && loggedInUser != null) {
//                        User currentUser = users.get(loggedInUser);
//                        String email = currentUser.getEmail();
//                        String password = currentUser.getPassword();
//                        request.setAttribute("email", email);
//                        request.setAttribute("password", password);
//                        request.setAttribute("loggedInUser", loggedInUser);
//                    }
//
//                    request.setAttribute("commentText", commentText);
//
//                    request.setAttribute("comments", comments);
//
//                    request.setAttribute("linkMap", users);
//                    if (loggedInUser == null || loggedInUser.equals("")) {
//                        //user is NOT logged in, set up a message and take them back to the index
//                        message = "Please login";
//                        url = "/login.jsp";
//                    } else {
//                        String errorMessage = "";
//                        String username = request.getParameter("userID");
//                        url = "/displaypost.jsp";
//                        // getting the post from the database
//                        LinkedHashMap<Integer, Post> posts = new LinkedHashMap();
//                        try {
//                            posts = PostDB.selectLast5Post(username);
//                            if (posts.isEmpty()) {
//                                Post post = new Post();
//                                post.setId(0);
//                                post.setTime("");
//                                post.setPost(username + " has not made any posts yet");
//                                post.setUsername(username);
//                                posts.put(0, post);
//                            }
//                        } catch (Exception e) {
//                        }
//
//                        request.setAttribute("username", username);
//                        message = "Welcome to " + username + "'s page";
//                        request.setAttribute("posts", posts);
//                        request.setAttribute("linkmap", users);
//                        request.setAttribute("comments", comments);
//                    }
//
//                    request.setAttribute("message", message);
//                }
//
//                break;
//
//            }
//            case "profile": {
//
//                if (loggedInUser == null || loggedInUser.equals("")) {
//                    //user is NOT logged in, set up a message and take them back to the index
//                    url = "/login.jsp";
//                    message = "Please login";
//                } else {
//                    url = "/profile.jsp";
//                    LinkedHashMap<Integer, Post> posts = new LinkedHashMap();
//                    try {
//                        posts = PostDB.selectAllCurrentUser(loggedInUser);
//                        request.setAttribute("posts", posts);
//                    } catch (Exception e) {
//                    }
//                    HashMap<String, User> users = new HashMap();
//                    try {
//                        users = UserDB.selectAll();
//                    } catch (SQLException e) {
//                    }
//                    if (users != null) {
//                        String userName = (String) session.getAttribute("loggedInUser");
//
//                        User currentUser = users.get(userName);
//
//                        String email = currentUser.getEmail();
//                        String password = currentUser.getPassword();
//                        request.setAttribute("email", email);
//                        request.setAttribute("password", password);
//                    }
//                }
//                break;
//            }
//
//            case "editUser": {
//                if (loggedInUser == null || loggedInUser.equals("")) {
//                    //user is NOT logged in, set up a message and take them back to the index
//                    message = "Please login";
//                    url = "login.jsp";
//                } else {
//                    //user is logged in
//                    String errorMessage = "";
//                    String userName = (String) session.getAttribute("loggedInUser");
//
//                    message = "Welcome " + userName + "<br>";
//                    HashMap<String, User> users = new HashMap();
//                    try {
//                        users = UserDB.selectAll();
//                    } catch (SQLException e) {
//                    }
//                    //create a list of users to pull logged in user from
//                    if (users != null) {
//                        //get current user's email and password from DB
//                        User currentUser = users.get(userName);
//                        String email = currentUser.getEmail();
//                        String password = currentUser.getPassword();
//                        String newEmail = request.getParameter("email");
//                        String newPassword = request.getParameter("password");
//                        request.setAttribute("email", email);
//                        request.setAttribute("password", password);
//                        //get edited password from form
//                        try {
//                            if (UserDB.getEmail().containsKey(newEmail)) {
//                                if (newEmail.equals(email)) {
//
//                                } else {
//                                    errorMessage += "This email address is already in use. <br>";
//                                }
//
//                            }
//
//                        } catch (Exception e) {
//                        }
//
//                        if ("".equals(newEmail)) {
//                            errorMessage += "Email must not be blank. <br>";
//                        }
//                        if (newEmail.length() < 5) {
//                            errorMessage += "Your email address isn't long enough.<br> ";
//                        }
//                        if (newEmail.contains("@") && newEmail.contains(".")) {
//                            if (newEmail.indexOf("@") < newEmail.indexOf(".")) {
//                                //valid email
//
//                            }
//                        } else {
//                            errorMessage += "Invalid email";
//                        }
//
//                        if ("".equals(newPassword)) {
//                            errorMessage += "Password must not be blank. <br>";
//                        }
//                        if (newPassword.length() <= 10) {
//                            errorMessage += "The password isn't long enough. <br>It must be over 10 characters. <br>";
//                        }
//
//                        if (errorMessage.isEmpty()) {
//
//                            if (newEmail.equals(email)) {
//                                message += "Email was unchanged <br>";
//                            } else {
//                                //update user Email in DB
//
//                                currentUser.setEmail(newEmail);
//                                message += "Email has changed. <br>";
//                            }
//                            if (newPassword.equals(session.getAttribute("loginPassword"))) {
//                                message += "Password was unchanged <br>";
//                            } else {
//
//                                String hash = "";
//
//                                SecretKeyCredentialHandler ch;
//
//                                try {
//                                    ch = new SecretKeyCredentialHandler();
//                                    ch.setAlgorithm("PBKDF2WithHmacSHA256");
//                                    ch.setKeyLength(256);
//                                    ch.setSaltLength(16);
//                                    ch.setIterations(4096);
//
//                                    hash = ch.mutate(newPassword);
//                                    currentUser.setPassword(hash);
//                                } catch (Exception ex) {
//
//                                }
//                                message += "Password has changed.";
//                                session.setAttribute("loginPassword", newPassword);
//                            }
//                            try {
//                                UserDB.userUpdate(currentUser, currentUser.getUserName());
//                            } catch (Exception e) {
//                            }
//                            request.setAttribute("email", newEmail);
//                            request.setAttribute("password", newPassword);
//                        } else {
//
//                            message = "You need to log in.";
//                            url = "/login.jsp";
//                        }
//
//                    }
//                }
//                break;
//            }

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
