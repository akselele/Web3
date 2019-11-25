<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Home</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<div id="container">
		<header>
			<h1>
				<span>Web shop</span>
			</h1>
			<nav>
				<ul>
					<li id ="actual"><a href="Servlet?command=showHome">Home</a></li>
					<li><a href="Servlet?command=overview">People</a></li>
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
			<h2>Home</h2>
			<c:choose>
				<c:when test = "${not empty text}">
					<p>Welcome, ${text}</p>
					<a href="Servlet?command=logOut">Log out</a>
				</c:when>
				<c:otherwise>
					<a href="Servlet?command=showLogIn">Log in</a>
				</c:otherwise>
			</c:choose>
		</header>
		<main> Sed ut perspiciatis unde omnis iste natus error sit
		voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque
		ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae
		dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit
		aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos
		qui ratione voluptatem sequi nesciunt.
				<a name="picture" href="Servlet?command=showPicture">Please, show my team</a>
			<c:if test="${foto == true}">
				<p>This is my team</p>
					<img src ="images/belgischHockeyTeam.jpg">
				<a href="Servlet?command=hidePicture">In future, don't show my picture anymore</a>
			</c:if>
		</main>
		<footer> &copy; Webontwikkeling 3, UC Leuven-Limburg </footer>
	</div>
</body>
</html>