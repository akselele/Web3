<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta charset="UTF-8">
    <title>Person Overview</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div id="container">
    <header>
        <h1><span>Web shop</span></h1>
        <nav>
            <ul>
                <li><a href="Servlet?command=showHome">Home</a></li>
                <li id="actual"><a href="Servlet?command=overview">Person Overview</a></li>
                <li><a href="Servlet?command=productoverview">Product Overview</a></li>
                <li><a href="Servlet?command=signUp">Sign up</a></li>
                <li><a href="Servlet?command=showAddProduct">Add product</a></li>
                <li><a href="Servlet?command=history">See history</a></li>
            </ul>
        </nav>
        <h2>
            User Overview
        </h2>

    </header>
    <main>
        <table>
            <tr>
                <th>E-mail</th>
                <th>First Name</th>
                <th>Last Name</th>
            </tr>
            <c:forEach var ="person" items="${persons}">
            <tr>
                <td>${person.email}</td>
                <td>${person.firstName}</td>
                <td>${person.lastName}</td>
                <td><a href="Servlet?command=deletePerson&userid=${person.userid}">Delete</a></td>
                <td><a href="Servlet?command=showCheck&userid=${person.userid}">Check Password</a></td>
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