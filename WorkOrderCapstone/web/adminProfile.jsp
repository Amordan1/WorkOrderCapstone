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
            <jsp:include page="AdminNav.jsp" />
            <h1>Edit Profile</h1>
         <h2><c:out value='${errorMessages}'/></h2>
            <h2><c:out value='${message}'/></h2>

            <div class="center">
                <form action="Public" method="post">
                    <label>User Name:</label>
                    <input type="text" disabled="disabled" name="userName" value="<c:out value='${user.userName}'/>">
                    <br>
                    <span name="userNameError" value="">${userNameError}</span>
                    <br>
                    
                    <label>Password:</label>
                    <input type="text" name="password" value="<c:out value='${user.password}'/>">
                    <span name="passwordError" value="">${passwordError}</span>
                    <br>
                    
                    <label>First Name:</label>
                    <input type="text" name="firstName" value="<c:out value='${user.firstName}'/>">
                    <span name="firstNameError" value="">${firstNameError}</span>
                    <br>
                    
                    <label>Last Name:</label>
                    <input type="text" name="lastName" value="<c:out value='${user.lastName}'/>">
                    <span name="lastNameError" value="">${lastNameError}</span>
                    <br>

                    <label>Phone:</label>
                    <input type="text" name="phone" value="<c:out value='${user.phone}'/>">
                    <br>
                    


                    <input type="submit" value="Submit Edit">
                </form>
            </div>
        </div>

    </body>
</html>
