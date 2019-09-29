<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="nl">
<head>
	<meta charset="UTF-8">
	<title>Bikes - view detail</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
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
		<section>
			<h2>Bike Detail</h2>
			<ul>
				<li>Brand: ${bike.brand}</li>
				<li>Type: ${bike.category}</li>
				<li>Description: ${bike.description}</li>
				<li>Price: €${bike.price}</li>
			</ul>
		</section>
		</main>
	</div>
</body>
</html>