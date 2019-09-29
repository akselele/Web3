<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="nl">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
	<title>Bikes</title>
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
			<h2>My Bikes</h2>
		
				<form action="Servlet?command=ontvangBrief" method="post">
					<p>
						<input type="checkbox" name="nieuwsbrief" value="Nieuwsbrief" <c:if test="${nieuws==true}"> checked </c:if>> Ja, ik wil de nieuwsbrief ontvangen <br>
					</p>
					<p>
						<input type="submit" value="Submit" formaction="Servlet?command=ontvangBrief">
					</p>
				</form>
		</section>
		</main>

	</div>
</body>
</html>
