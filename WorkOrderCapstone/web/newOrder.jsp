<%-- 
    Document   : displaypost
    Created on : Apr 14, 2022, 2:19:37 PM
    Author     : nguye
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post Page</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <div class="wrapper">
        <jsp:include page="LoggedInNav.jsp" />
        <body>
            <div class="center">
                <h1>New Order</h1>
                <h2><c:out value='${errorMessage}'/></h2>
                <h2><c:out value='${message}'/></h2>

                <form action="Private" method="post">
                    <input type="hidden" name="action" value="createOrder">

                    <label>New order for</label>
                    <input style="width: unset; margin-left: unset;" disabled="disabled" type="text" name="userName" value="${loggedInUser}">
                    <br>

                    <label>Password</label>
                    <input type="password" name="password" value="">
                    <br>

                    <input type="submit" value="Submit">
                </form>
            </div>
        </body>
    </div>
</html>
