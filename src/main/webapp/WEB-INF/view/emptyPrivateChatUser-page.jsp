<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
		<title>Private Messaging</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
   		<script src="https://cdnjs.cloudflare.com/ajax/libs/chosen/1.8.7/chosen.jquery.min.js"></script>
   		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/chosen/1.8.7/chosen.min.css">
      	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
		<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.js"></script>
		<link type="text/css" rel="stylesheet" href="/css/bootstrap.css"/>
		<link type="text/css" rel="stylesheet" href="/css/chat.css"/>
		<link type="text/css" rel="stylesheet" href="/css/custom_chosen.css"/>
		<link type="text/css" rel="stylesheet" href="/css/custom.css"/>
	</head>
	<body>
		<div>
			<div class="float-left color-red-variant1">
				<h1>Hi, ${sessionUser.firstName}</h1>
			</div>
			<div >
				<form:form action="logout" method="post">
				    <input type="submit" class="btn btn-outline float-right margin-top-15" value="Logout"/>
				</form:form>
			</div>
		</div>
		<br><br><br>
		<form:form action="privateChatUser" method="get" modelAttribute="privateChat">
			<div align="center">
				<form:select path="selectedUser" class="form-control chosen-select" tabindex="2">
					<option disabled="disabled" selected>Select User</option>
					<c:forEach items="${users}" var="user">
						<form:option class="left-align" value="${user.value[0]} - ${user.value[1]} ${user.value[2]}"/>
					</c:forEach>
		        </form:select>
		        <input type="submit" class="btn btn-outline" value="Submit"/>
			</div>
		</form:form>
		
		<div class="container-fluid h-100">
			<div class="row justify-content-center h-100">
				<div class="col-md-4 col-xl-3 chat margin-top-15">
					<div class="card mb-sm-3 mb-md-0 contacts_card">
						<div class="card-header"></div>
						<div class="card-body contacts_body">
							<ui class="contacts">
							<li class="active">
								<div class="d-flex bd-highlight">
									<div class="user_info">
										<span>No User Available</span>
									</div>
								</div>
							</li>
							</ui>
						</div>
						<div class="card-footer"></div>
					</div>
				</div>
				<div class="col-md-8 col-xl-6 chat margin-top-15">
					<div class="card">
						<div class="card-header msg_head">
							<div class="d-flex bd-highlight">
								<div class="user_info">
									<span>Select atleast one user to chat</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<script>
	    	var config = {
		      '.chosen-select'           : {},
		      '.chosen-select-deselect'  : {allow_single_deselect:true},
		      '.chosen-select-no-single' : {disable_search_threshold:10},
		      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
		      '.chosen-select-width'     : {width:"50%"}
		    }
		    for (var selector in config) {
		      $(selector).chosen(config[selector]);
		    }
 	 	</script>
	</body>
</html>
