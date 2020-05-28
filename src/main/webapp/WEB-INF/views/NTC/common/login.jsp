<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<meta charset="utf-8">
<link rel="icon" href="<c:url value="/resources/adminltd/images/favicon.ico" />" type="image/x-icon">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	
	<title>Nepal Telecom | Log in</title>
	<link media="all" rel="stylesheet" href="<c:url value="/resources/adminltd/css/login.css" />">
	
</head>
<!-- kk-->
<body>
<div id="wrapper">
		<div class="ntc-logo">
			<a href="#"><img src="<c:url value="/resources/adminltd/images/logo.png" />" alt="NTC"></a>
		</div>
		<div class="login-holder">
			<div class="banner-image">
				<img src="<c:url value="/resources/adminltd/images/FTTH-Nepal-Telecom_logo.jpg" />" alt="image description">
			</div>
			<div class="heading-block">
				${fx}
			</div>
			<div class="heading-block">
			<c:choose>
				<c:when test="${not empty error}">
      			  ${error}
    				</c:when>
				<c:otherwise>Sign in to start your session</c:otherwise>
			</c:choose>
			</div>
			<form class="login-form" action="<c:url value="/postLogIn" />" method="post"
				acceptCharset="UTF-8" >
				
				<span class="text">Sign in to start your session</span>
				<div class="input-holder">
				
				<input type="text" name="USER_ID" class="form-control"
						placeholder="USER" required="required">	 
						
					<div class="ico"><img src="<c:url value="/resources/adminltd/images/mail.png" />" alt="NTC image"></div>
					
				</div>
				<div class="input-holder">
				
					<input type="password" name="PASSWORD" class="form-control"
						placeholder="Password" required="required">
					
					
					<div class="ico"><img src="<c:url value="/resources/adminltd/images/lock.png" />" alt="Complain Management System"></div>
				</div>
				<input type="submit" value="Sign in">
				
				<a  href="forgotpassword"
								class="forget-password">Forgot Password</a>
								
			</form>
			
		
								
		</div>
	</div>
</body>
<!--end kk-->


</html>
