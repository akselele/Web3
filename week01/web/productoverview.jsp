<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta charset="UTF-8">
    <title>Product Overview</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div id="container">
    <header>
        <h1><span>Web shop</span></h1>
        <nav>
            <ul>
                <li><a href="Servlet">Home</a></li>
                <li><a href="Servlet?command=overview">Person Overview</a></li>
                <li id="actual"><a href="Servlet?command=productoverview">Product Overview</a></li>
                <li><a href="Servlet?command=signUp">Sign up</a></li>
                <li><a href="Servlet?command=showAddProduct">Add product</a></li>
                <li><a href="Servlet?command=history">See history</a></li>
            </ul>
        </nav>
        <h2>
            Product Overview
        </h2>

    </header>
    <main>
        <table>
            <tr>
                <th>Name</th>
                <th>Price</th>
                <th>Description</th>
            </tr>
            <c:forEach var ="product" items="${products}">
                <tr>
                    <td>${product.name}</td>
                    <td>${product.price}</td>
                    <td>${product.description}</td>
                    <td><a href="Servlet?command=deleteProduct&itemid=${product.productId}">Delete</a></td>
                </tr>
            </c:forEach>

            <caption>Products Overview</caption>
        </table>
    </main>
    <footer>
        &copy; Webontwikkeling 3, UC Leuven-Limburg
    </footer>
</div>
</body>
</html>