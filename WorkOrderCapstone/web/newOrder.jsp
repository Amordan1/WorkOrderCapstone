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

                    <label>New Order For:</label>
                    <input style="width: unset; margin-left: unset;" disabled="disabled" type="text" name="userName" value="${loggedInUser}">
                    <br><br>

                    <label style="padding-left: 0; float: unset; padding-bottom: 1em;">Description of Work Order</label><br>
                    <div style="display: flex; justify-content: center; align-items: center"><textarea type="text" style="height: 100; width: 400; resize: none; align-self: center" name="orderText" rows='10'cols='100' autofocus='true' maxlength='2000'><c:out value='${orderText}'/></textarea></div>
                    <br><br><br>

                    <input type="submit" value="Submit Order">
                </form>
            </div>
        </body>
    </div>
</html>
