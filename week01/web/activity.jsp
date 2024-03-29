<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta charset="UTF-8">
    <title>Overview</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div id="container">
    <header>
        <h1><span>Web shop</span></h1>
        <nav>
            <ul>
                <li><a href="Servlet?command=showHome">Home</a></li>
                <li><a href="Servlet?command=overview">People</a></li>
                <li><a href="Servlet?command=productoverview">Products</a></li>
                <li><a href="Servlet?command=signUp">Sign up</a></li>
                <li><a href="Servlet?command=showAddProduct">Add product</a></li>
                <li><a href="Servlet?command=cart">Cart</a></li>
            </ul>
        </nav>
        <h2>
            User Overview
        </h2>

    </header>
    <main>
        <table>
            <tr>
                <th>Time</th>
                <th>Type</th>
            </tr>
            <c:forEach var ="history" items="${history}">
                <tr>
                    <td>${history.key}</td>
                    <td>${history.value}</td>
                </tr>
            </c:forEach>

            <caption>Users Overview</caption>
        </table>
    </main>
    <footer>
        &copy; Webontwikkeling 3, UC Leuven-Limburg
    </footer>
</div>
</body>
</html>