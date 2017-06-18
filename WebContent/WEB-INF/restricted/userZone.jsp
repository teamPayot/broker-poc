<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Zone</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form.css" />
</head>

<body>

	<p>Ici on fait des trucs secrets... Bienvenue ${sessionScope.userSession.email}</p>
	<br />
	<p>Vas-y ma gueule, clique... Allez clique...</p>

	<form method="post" action="<c:url value="/restricted/userZone"/>">
		<input type="submit" value="Click"/>
	</form>

	<c:if test="${click == 1}">
		<h1>BOOM ! Je t'avais dis quoi ma gueule??? DAR!</h1><br />

		<form method="get" action="<c:url value="/logout"/>">
			<input type="submit" value="Logout"/>
		</form>

		<c:import url="http://www.bonjourmademoiselle.fr/" />
		
	</c:if>
</body>
</html>