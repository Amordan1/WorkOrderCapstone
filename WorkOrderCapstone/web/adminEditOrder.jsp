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
                <input type="hidden" name="action" value="adminSubmitEdit"> 
                <input type="hidden" name="idValue" value="${idValue}"> 
                
                <div><label>Est. End Date:</label>
                <input type="date" name="userName" value="<c:out value='${estEndDate}'/>">
                <br>
                <span name="userNameError" value="">${estEndDateError}</span>
                <br>

                <label>Est. Price:</label>
                <input type="text" name="email" value="${estPrice}">
                <span name="emailError" value="">${estPriceError}</span>
                <br></div>
                <input type="submit" value="Submit Edit">
            </form>
        </div>
    </body>
</html>
