<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
		<title>Login</title>
		
		<link type="text/css" rel="stylesheet" href="/css/materialize.css"/>
		<link type="text/css" rel="stylesheet" href="/css/custom.css"/>
    	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	</head>
	<body class="bg-custom">
		<div class="row">
			<div class="col m12 offset-m2">
				<h1 class="main_headline">Private Messaging Application</h1>
			</div>
		</div>
	
		<div class="container">
			<div class="row">
				<div class="col m6 offset-m3">
					<div class="card rgba-bg">
						<div class="card-content center">
							<h3 class="color-mat-default">Login</h3>
							
							<c:if test="${param.error != null}">
								<div class="error">
									* Invalid UserName and Password !
								</div>
							</c:if>
							
							<form:form>
								<div class="input-field">
							        <i class="material-icons prefix">account_circle</i>
							        <input id="username" class="validate color-white" type="text" name="username"/>
									<label for="username">User Name</label>
								</div>
								<div class="input-field">
									<i class="material-icons prefix">lock</i>
									<input id="password" class="validate color-white" type="password" name="password"/>
							        <label for="password">Password</label>
								</div>      
							    
							    <input type="submit" class="btn btn-outline btn-block" value="login"/>
							</form:form>
							
							<br>
							<form:form action="/" method="get">
							    <input type="submit" class="btn btn-outline btn-block" value="register"/>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<script src="/js/materialize.min.js"></script>
	</body>
</html>