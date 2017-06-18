<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Sign in</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form.css" />
	</head>
	
	<body>
		<form method="post" action="<c:url value="/signin"/>">
			<fieldset>
				<legend>Sign up</legend>
				
				<label for="email">Email <span class="required">*</span></label>
				<input type="email" id="email" name="email" value="<c:out value="${user.email}"/>" size="20" maxlength="60" required />
				<span class="error"><c:out value="${controller.errors['email']}" /></span>
				<br />
				
				<label for="passw">Password <span class="required">*</span></label>
				<input type="password" id="passw" name="password" size="20" maxlength="40" required />
				<span class="error"><c:out value="${controller.errors['password']}" /></span>
				<br />
				
				<input type="submit" value="Sign up" class="noLabel" />
				<br />
				
				<p class="${empty controller.errors ? 'success' : 'error'}"><c:out value="${controller.result}" /></p>
				
				<c:if test="${!empty sessionScope.userSession}">
					<p class="success">You have been successfully connected with email : ${sessionScope.userSession.email}</p>
				</c:if>
			</fieldset>
		</form>
	</body>

</html>