<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>

<table border="1">
    <thead>
    <tr>
        <th>name</th>
        <th>address</th>
        <th>phone</th>
        <th>Birthday</th>
    </tr>
    </thead>
    <c:forEach items="${guest}" var="guest">
        <tr>
            <td><c:out value="${guest.name}"/></td>
            <td><c:out value="${guest.address}"/></td>
            <td><c:out value="${guest.phone}"/></td>
            <td><c:out value="${guest.birthDate}"/></td>
            <td><form method="post" action="${pageContext.request.contextPath}/books/delete?id=${guest.id}"
                      style="margin-bottom: 0;"><input type="submit" value="Delete"></form></td>
        </tr>
    </c:forEach>
</table>

<h2>Select a guest</h2>
<c:if test="${not empty chyba}">
    <div style="border: solid 1px red; background-color: yellow; padding: 10px">
        <c:out value="${chyba}"/>
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/guest/add" method="post">
    <table>
        <tr>
            <th>Guest name:</th>
            <td><input type="text" name="name" value="<c:out value='${param.name}'/>"/></td>
        </tr>
        <tr>
            <th>Guest address:</th>
            <td><input type="text" name="address" value="<c:out value='${param.address}'/>"/></td>
        </tr>
         <tr>
            <th>Guest phone:</th>
            <td><input type="text" name="phone" value="<c:out value='${param.phone}'/>"/></td>
        </tr>
        <tr>
            <th>Guest birthDate:</th>
            <td><input type="text" name="birthDate" value="<c:out value='${param.birthDate}'/>"/></td>
        </tr>
    </table>
    <input type="Submit" value="Zadat" />
</form>

</body>
</html>