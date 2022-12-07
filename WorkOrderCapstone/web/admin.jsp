<%-- 
    Document   : target
    Created on : Sep 21, 2021, 1:11:03 PM
    Author     : fs148523
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <div class="wrapper">
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Login Page</title>
            <link rel="stylesheet" href="styles/main.css" type="text/css"/>
        </head>
        <jsp:include page="nav.jsp" /> 
        <body>
            <h1>Admin Login</h1>
            <div class="center">
                <h2>${message}</h2>
                <!--login page-->
                <form action="Private" method="post">
                    <input type="hidden" name="action" value="adminLogin">

                    <label>User Name</label>
                    <input style="width: unset; margin-left: unset;" type="text" name="userName" value="">
                    <br>

                    <label>Password</label>
                    <input type="password" name="password" value="">
                    <br>

                    <input type="submit" value="Login">
                </form>
            </div>
        </body>
    </div>
</html>
