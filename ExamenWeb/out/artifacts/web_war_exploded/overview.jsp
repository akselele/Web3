<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="nl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>Overview Bikes</title>
    <link rel="stylesheet" media="all" href="css/reset.css">
    <link rel="stylesheet" media="all" href="css/project.css">
    <link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
</head>

<body>
<header>
    <h1>
        <img src="images/bikesBanner.png" alt="banner My Bikes">
    </h1>

    <nav class="nav">
        <ul>
            <li><a href="Servlet">Home</a></li>
            <li><a href="Servlet?command=overview">Overview</a></li>
            <li><a href="Servlet?command=add">Toevoegen</a></li>
            <c:if test="${nieuws == true}"><li><a href="" method="post">Nieuwsbrief</a></li></c:if>
        </ul>
    </nav>
</header>
<div class="container">
    <main>
        <c:choose>
        <c:when test = "${bike == null}">
            <p>No products to show</p>
        </c:when>
        <c:otherwise>
        <section>
            <h2>Overview Bikes</h2>
            <table id="overview">
                <thead>
                <tr>
                    <th>Brand</th>
                    <th>Category</th>
                </tr>
                </thead>
                <c:forEach var="bike" items="${bikeHistory}">
                    <tbody>
                    <tr>
                        <td>${bike.brand}</td>
                        <td>${bike.category}</td>
                        <td><a href="Servlet?command=details&itemid=${bike.itemId}">Details</a></td>
                    </tr>
                    </tbody>
                </c:forEach>
            </table>
            </c:otherwise>
            </c:choose>

        </section>
    </main>
</div>
</body>
</html>