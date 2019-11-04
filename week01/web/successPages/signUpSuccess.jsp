<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Success Sign Up</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div id="container">
    <header>
        <h1>
            <span>Success Sign Up</span>
        </h1>
        <nav>
            <ul>
                <li><a href="Servlet">Home</a></li>
                <li><a href="Servlet?command=overview">Person Overview</a></li>
                <li><a href="Servlet?command=productoverview">Product Overview</a></li>
                <li><a href="Servlet?command=signUp">Sign up</a></li>
                <li><a href="Servlet?command=showAddProduct">Add product</a></li>
                <li><a href="Servlet?command=history">See history</a></li>
            </ul>
        </nav>

    </header>
    <main>
        <p>Your account has successfully been created, ${param.firstname}.</p>
        <a href="Servlet?command=showHome">Return to home page</a>
    </main>
    <footer> Videos are not mine and are videos of the respecting players </footer>
</div>
</body>
</html>