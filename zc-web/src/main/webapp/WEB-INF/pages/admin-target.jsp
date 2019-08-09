<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>admin target</title>
</head>
<body>
    <c:if test="${!empty list}">
        <c:forEach items="${list}" var="item">
            ${item}<br>
        </c:forEach>
    </c:if>
</body>
</html>
