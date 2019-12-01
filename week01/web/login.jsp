<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta charset="UTF-8">
    <title>Log In</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div id="container">
    <header>
        <h1><span>Web shop</span></h1>
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
        <h2>
            Log In
        </h2>

    </header>
    <main>
        <c:if test = "${not empty error}">
            <div class="alert-danger">
                <ul>
                        <li>${error}</li>
                </ul>
            </div>
        </c:if>

        <form method="post" action="Servlet?command=logIn" novalidate="novalidate">
            <!-- novalidate in order to be able to run tests correctly -->
            <p><label for="email">Email</label><input type="email" id="email" name="email" required value ="${fn:escapeXml(emailPreviousValue)}"></p>
            <p><label for="password">Password</label><input type="password" id="password" name="password"
                                                            required value ="${fn:escapeXml(PasswordPreviousValue)}"></p>
            <p><input type="submit" id="logIn" value="Log In"></p>

        </form>
    </main>
    <footer>
        &copy; Webontwikkeling 3, UC Leuven-Limburg
    </footer>
</div>
</body>
</html>
