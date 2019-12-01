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
                <li><a href="Servlet?command=showHome">Home</a></li>
                <c:if test="${person.role=='ADMIN'}">
                    <li><a href="Servlet?command=overview">People</a></li>
                </c:if>
                <li><a href="Servlet?command=productoverview">Products</a></li>
                <c:if test =" ${empty person}">
                    <li><a href="Servlet?command=signUp">Sign up</a></li>
                </c:if>
                <c:if test="${person.role=='ADMIN'}">
                    <li><a href="Servlet?command=showAddProduct">Add product</a></li>
                </c:if>
                <li><a href="Servlet?command=cart">Cart</a></li>
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