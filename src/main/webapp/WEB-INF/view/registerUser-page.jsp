<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
		<title>Registration</title>
		
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
							<c:if test="${registrationSuccess != null}">
								<div class="center"><span class="success-message font-size-20">${registrationSuccess}</span></div>
							</c:if>
						
							<h3 class="color-mat-default">Registration</h3>
							<form:form action="processUserRegistration" method="post" modelAttribute="userInfo">
						    	<div class="input-field">
						    		<i class="material-icons prefix">badge</i>
							    	<form:input id="userId" class="validate color-white" path="userId"/>
							    	<label for="userId">User Name</label>
							    	<form:errors path="userId" class="error"/>
							    </div>
						    	<div class="input-field">
						    		<i class="material-icons prefix">account_circle</i>
							    	<form:input id="firstName" class="validate color-white" path="firstName"/>
							    	<label for="firstName">First Name</label>
							    	<form:errors path="firstName" class="error"/>
							    </div>
							    <div class="input-field">
							    	<i class="material-icons prefix">account_circle</i>
							    	<form:input id="lastName" class="validate color-white" path="lastName"/>
							    	<label for="lastName">Last Name</label>
							    	<form:errors path="lastName" class="error"/>
							    </div>
								<div class="input-field">
									<i class="material-icons prefix">lock</i>
							    	<form:password id="password" class="validate color-white" path="password"/>
							    	<label for="password">Password</label>
							    	<form:errors path="password" class="error"/>
							    </div>
							    <div class="input-field">
							    	<i class="material-icons prefix">enhanced_encryption</i>
							    	<form:password id="confirmPassword" class="validate color-white" path="confirmPassword"/>
							    	<label for="confirmPassword">Confirm Password</label>
							    	<form:errors path="confirmPassword" class="error"/>
							    </div>
						           
						        <input type="submit" class="btn btn-outline btn-block" value="Register"/>
							</form:form>
							
							<br>
							<form:form action="privateChatUser" method="get">
							    <input type="submit" class="btn btn-outline btn-block" value="login"/>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<script src="/js/materialize.min.js"></script>
	</body>
</html>