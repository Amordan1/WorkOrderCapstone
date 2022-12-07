<%-- 
    Document   : editPost
    Created on : Apr 19, 2022, 9:22:26 PM
    Author     : Jeremy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Order</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="nav.jsp" />
            <h1>Edit your Order</h1>
            <h2>${message}</h2>
            <form action="Private" method="post">
                <input type="hidden" name="action" value="submitEdit"> 
                <input type="hidden" name="idValue" value="${idValue}"> 
                <div style="display: flex; justify-content: center; align-items: center"><textarea type="text" style="height: 100; width: 400; resize: none; align-self: center" name="orderText" rows='10'cols='100' autofocus='true' maxlength='2000'>${orderText}</textarea></div>                <br>
                <input type="submit" value="Submit Edit">
            </form>
        </div>
    </body>
</html>
