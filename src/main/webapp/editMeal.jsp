<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>Edit meal</title>
</head>
<body>
<section>
    <h1><%=meal.getId() == 0 ? "Create meal" : "Update meal"%></h1>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>date/time</dt>
            <dd><input type="datetime-local" name="dateTime" size=30 value="${meal.dateTime}"></dd>
        </dl>
        <dl>
            <dt>description</dt>
            <dd><input type="text" name="description" size=30 value="${meal.description}"></dd>
        </dl>
        <dl>
            <dt>calories</dt>
            <dd><input type="number" name="calories" size=30 value="${meal.calories}"></dd>
        </dl>
        <button type="submit">Сохранить</button>
    </form>
</section>
</body>
</html>
