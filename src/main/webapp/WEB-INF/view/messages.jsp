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
						<div class="card-header">
							<div class="input-group">
								<div class="form-control search">
									<div class="row justify-content-center recent_users">
										Recent Users
									</div>
								</div>
								<span class="input-group-text search_btn"></span>
							</div>
						</div>
						<div class="card-body contacts_body">
							<ui class="contacts">
								<c:forEach items="${recentUserData}" var="recentUser">
									<li class="active">
										<div class="d-flex">
											<form:form action="messageSentUser" method="post" modelAttribute="privateChat">
												<input class="recent-user-bar" type="submit" value="${recentUser.value[1]} ${recentUser.value[2]}"/>
												<form:hidden path="selectedUser" value="${recentUser.value[0]}-${recentUser.value[1]} ${recentUser.value[2]}"/>
												<form:hidden path="message" value=""/>
												<c:if test="${recentUser.value[3]=='Unread' && recentUser.value[4]!=sessionUser.userId}">
													<div class="margin-top-15 margin-left-60">
														<p class="color-lime">${recentUser.value[5]} Unread Messages !</p>
													</div>
												</c:if>
											</form:form>
										</div>
									</li>
								</c:forEach>
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
									<span>Chat with ${selectedUserName}</span>
									<p>User Id: ${selectedUserId}</p>
									<div class="color-green">
										<p>${messageCount} Messages</p>
									</div>
								</div>
							</div>
						</div>
						<div id="messages" class="card-body msg_card_body">
							<c:forEach items="${chats}" var="chat">
								<c:choose>
								    <c:when test="${chat.value[0]!=sessionUser.userId}">
								        <div class="d-flex justify-content-start mb-4">
											<div class="msg_cotainer">
												${chat.value[2]}
												<span class="msg_time">${chat.value[3]}</span>
											</div>
										</div>
								    </c:when>    
								    <c:otherwise>
								        <div class="d-flex justify-content-end mb-4">
											<div class="msg_cotainer_send">
												${chat.value[2]}
												<span class="msg_time_send">${chat.value[3]}</span>
											</div>
										</div>
								    </c:otherwise>
								</c:choose>
							</c:forEach>
							
						</div>
						<div class="card-footer">
							<form:form action="messageSentUser" method="post" modelAttribute="privateChat">
								<div>
									<div class="left-div-chat">
										<form:input id="msg" path="message" class="form-control type_msg" placeholder="Type your message..."/>
									</div>
									<div class="right-div-chat">
										<button class="send_btn" type="submit">
										    <i class="fa fa-paper-plane color-blue-variant1" aria-hidden="true"></i>
										</button>
									</div>
									<form:hidden path="selectedUser" value="${selectedUserId}-${selectedUserName}"/>
								</div>
							</form:form>
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
 	 	<script>  
 	 		document.getElementById('msg').value='';
 	 		
 	 		let chatWrapper = document.querySelector('#messages');
 	 		chatWrapper.scrollTo(0, Number.MAX_SAFE_INTEGER);
 	 	</script>
	</body>
</html>
