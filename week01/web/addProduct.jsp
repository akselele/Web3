<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta charset="UTF-8">
    <title>Add product</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div id="container">
    <header>
        <h1><span>Web shop</span></h1>
        <nav>
            <ul>
                <li><a href="Servlet?command=showHome">Home</a></li>
                <li><a href="Servlet?command=overview">Person Overview</a></li>
                <li><a href="Servlet?command=productoverview">Product Overview</a></li>
                <li><a href="Servlet?command=signUp">Sign up</a></li>
                <li id="actual"><a href="Servlet?command=showAddProduct">Add product</a></li>
                <li><a href="Servlet?command=history">See history</a></li>
            </ul>
        </nav>
        <h2>
            Sign Up
        </h2>

    </header>
    <main>
        <c:if test = "${not empty errors}">
            <div class="alert-danger">
                <ul>
                    <c:forEach items="${errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <form method="post" action="Servlet?command=addProduct" novalidate="novalidate">
            <!-- novalidate in order to be able to run tests correctly -->
            <p><label for="name">Name</label><input type="text" id="name" name="name"
                                                         required value ="${fn:escapeXml(productNamePreviousValue)}"></p>
            <p><label for="price">Price</label><input type="text" id="price" name="price"
                                                               required value ="${fn:escapeXml(pricePreviousValue)}"></p>
            <p><label for="description">Description</label><input type="text" id="description" name="description"
                                                             required value ="${fn:escapeXml(descriptionPreviousValue)}"></p>
            <p><input type="submit" id="addProduct" value="Add Product"></p>

        </form>
    </main>
    <footer>
        &copy; Webontwikkeling 3, UC Leuven-Limburg
    </footer>
</div>
</body>
</html>
