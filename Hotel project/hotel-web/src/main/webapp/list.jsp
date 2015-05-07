<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>

<table border="1">
    <thead>
    <tr>
        <th>Floor</th>
        <th>Number</th>
        <th>Capacity</th>
    </tr>
    </thead>
    <c:forEach items="${rooms}" var="room">
        <tr>
            <td><c:out value="${room.floor}"/></td>
            <td><c:out value="${room.number}"/></td>
            <td><c:out value="${room.capacity}"/></td>
            <td><form method="post" action="${pageContext.request.contextPath}/hotel/delete?id=${room.id}"
                      style="margin-bottom: 0;"><input type="submit" value="Smazat"></form></td>
        </tr>
    </c:forEach>
</table>

<h2>Enter room</h2>
<c:if test="${not empty error}">
    <div style="border: solid 1px red; background-color: yellow; padding: 10px">
        <c:out value="${error}"/>
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/hotel/add" method="post">
    <table>
        <tr>
            <th>Floor:</th>
            <td><input type="text" name="floor" value="<c:out value='${param.floor}'/>"/></td>
        </tr>
        <tr>
            <th>Number:</th>
            <td><input type="text" name="number" value="<c:out value='${param.number}'/>"/></td>
            
        </tr>
        <tr>
            <th>Capacity:</th>
            <td><input type="text" name="capacity" value="<c:out value='${param.capacity}'/>"/></td>
            
        </tr>
    </table>
    <input type="Submit" value="Zadat" />
</form>

</body>
</html>