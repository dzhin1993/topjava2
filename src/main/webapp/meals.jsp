<%@ page import="ru.javawebinar.topjava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Meals</title>
</head>
<body>
<h1>Meals list</h1>
<a href="meals?action=add"><img src="img/add.png"></a>
<table>
        <tr>
            <td>date/time</td>
            <td>description</td>
            <td>calories</td>
            <td>delete</td>
            <td>edit</td>
        </tr>
    <c:forEach var="meal" items="${meals}">
        <jsp:useBean id="meal" class="ru.javawebinar.topjava.model.MealTo"/>
        <tr class="${meal.excess ? 'excess' : 'notExcess'}">
            <td><%=DateUtil.format(meal.getDateTime())%></td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?id=${meal.id}&action=delete"><img src="img/delete.png"></a></td>
            <td><a href="meals?id=${meal.id}&action=edit"><img src="img/pencil.png"></a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
