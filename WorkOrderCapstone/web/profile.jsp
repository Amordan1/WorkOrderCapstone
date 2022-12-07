<%-- 
   Document   : profile
   Created on : 4/17/22, 10:56:23 AM
   Author     : jwortmann
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <div class="wrapper">
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Profile Page</title>
            <link rel="stylesheet" href="styles/main.css" type="text/css"/>
        </head>
        <jsp:include page="LoggedInNav.jsp" /> 
        <body>
            <h1>Users Information</h1>
            <h2><c:out value='${errorMessage}'/></h2>
            <h2>${message}</h2>
            <form>
                <input type="hidden" name="action" value="logout"> 
                <td><input type="submit" value="Logout"></td>
            </form>
            <!--        listed user info and allow to edit-->
            <div class="center">
                <form action="Private" method="post">
                    <input type="hidden" name="keyEdit" value="<c:out value='${user.getUserName()}'/>">
                    <input type="hidden" name="action" value="edit" >


                    <label>Email</label>
                    <input type="text" name="email" value="<c:out value='${email}'/>">
                    <br>

                    <label>Password</label>
                    <input type="text" name="password" value="<c:out value='${loginPassword}'/>">
                    <br>


                    <input type="submit" value="Edit">

                </form>
                <br>

                <%-- 
        This is where users create their posts. 
                --%>
                <form action="Private" method="post">
                    <input type="hidden" name="action" value="createPost"> 
                    <label style="padding-left: 0; float: unset; padding-bottom: 1em;">Create a post</label><br>
                    <textarea type="text" style="height: 100; width: 400;" name="postText" rows='10'cols='100' autofocus='true' maxlength='1024'><c:out value='${postText}'/></textarea>
                    <br><br><br>
                    <input type="submit" value="Create Post">

                </form>
                <br>
                <%-- 
        This is where the user's comments are displayed. They are displayed by time in
                descending order (latest first). 
                --%>
                <table style="margin: auto">
                    <tr>
                        <th>Time Stamp</th>
                        <th><c:out value='${loggedInUser}'/> Posts</th>
                        <th></th>
                        <th></th>
                        <th></th>
                    </tr>
                    

                    <c:forEach  var="p" items="${posts}" >
                        <tr>

                           <td><c:out value='${p.value.time}'/></td>
                           <td><c:out value='${p.value.post}'/></td>
                        <form>
                            <input type="hidden" name="action" value="editPost"> 
                            <input type="hidden" name="idValue"  value="<c:out value='${p.value.id}'/>">
                            <td><input type="submit" value="Edit Post"></td>
                        </form>
                        <form>
                            <input type="hidden" name="action" value="deletePost"> 
                            <input type="hidden" name="idValue" value="<c:out value='${p.value.id}'/>">
                            <td><input type="submit" value="Delete Post"></td>
                        </form>

                        </tr>
                    </c:forEach>  
                </table>
            </div>
        </body>
    </div>
</html>
