<%-- 
    Document   : index
    Created on : Sep 21, 2021, 1:02:53 PM
    Author     : fs148523
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Java Project-Registration Form</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="nav.jsp" />
            <h1>Social Media Fun Site Registration!</h1>
         <h2><c:out value='${errorMessage}'/></h2>
            <h2><c:out value='${message}'/></h2>

            <div class="center">
                <form action="Public" method="post">
                    <input type="hidden" name="action" value="add" >

                    <label>User Name:</label>
                    <input type="text" name="userName" value="<c:out value='${user.userName}'/>">
                    <br>

                    <label>Email:</label>
                    <input type="text" name="email" value="<c:out value='${user.email}'/>">
                    <br>

                    <label>Password:</label>
                    <input type="text" name="password" value="<c:out value='${user.password}'/>">
                    <br>
                    
                    <label>First Name:</label>
                    <input type="text" name="firstName" value="<c:out value='${user.firstName}'/>">
                    <br>
                    
                    <label>Last Name:</label>
                    <input type="text" name="lastName" value="<c:out value='${user.lastName}'/>">
                    <br>

                    <label>Phone:</label>
                    <input type="text" name="phone" value="<c:out value='${user.phone}'/>">
                    <br>
                    
                    <label>Street Address:</label>
                    <input type="text" name="street" value="<c:out value='${user.street}'/>">
                    <br>
                    
                    <label>City:</label>
                    <input type="text" name="city" value="<c:out value='${user.city}'/>">
                    <br>
                    
                    <label>State:</label>
                    <input type="text" name="state" value="<c:out value='${user.state}'/>">
                    <br>
                    
                    <label>ZIP:</label>
                    <input type="text" name="zip" value="<c:out value='${user.zip}'/>">
                    <br>

                    <input type="submit" value="Register!">
                </form>
            </div>
        </div>

    </body>
</html>
