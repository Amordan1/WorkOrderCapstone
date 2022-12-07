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
            <div class="center" style="width: 90%;">
                <h1>My Work Orders</h1>
                <h2><c:out value='${errorMessage}'/></h2>
                <h2><c:out value='${message}'/></h2>

                <table>

                    <thead>
                        <th>Work Order ID</th>
                        <th>Description</th>
                        <th>Start Date</th>
                        <th>Est. End Date</th>
                        <th>End Date</th>
                        <th>Est. Price</th>
                        <th>Fulfilled</th>

                        
                        <th></th>
                        <th></th>
                    </thead>
                        <c:forEach  var="item" items="${items}" >

                        <tr>

                            <td><c:out value='#${item.value.workOrderID}'/></td>
                            <td><c:out value='${item.value.workOrderDesc}'/></td>
                            <td><c:out value='${item.value.workOrderStart}'/></td>
                            <td><c:out value='${item.value.workOrderEstEnd}'/></td>
                            <td><c:out value='${item.value.workOrderEnd}'/></td>
                            <td><c:out value='${item.value.estPrice}'/></td>
                            <td><c:out value='${item.value.complete}'/></td>
                            <form action="Private" method="post">
                                <input type="hidden" name="action" value="cancelOrder"> 
                                <input type="hidden" name="idValue"  value="<c:out value='${item.value.workOrderID}'/>">
                                <td><input type="submit" value="Cancel Order"></td>
                            </form>
                            <form action="Private" method="post">
                                <input type="hidden" name="action" value="editOrder"> 
                                <input type="hidden" name="orderID"  value="<c:out value='${item.value.workOrderID}'/>">
                                <td><input type="submit" value="Edit Order"></td>
                            </form>
                        </tr>
                        
                        </c:forEach>  
                </table>
            </div>
        </body>
    </div>
</html>
