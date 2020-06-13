<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Nepal Telecom | Log in</title>
<!-- Tell the browser to be responsive to screen width -->

<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.7 -->
<link href="<c:url value="/resources/adminltd/css/bootstrap.min.css" />"
	rel="stylesheet">

<!-- Font Awesome -->


<link
	href="<c:url value="/resources/adminltd/css/font-awesome.min.css" />"
	rel="stylesheet">

<!-- Ionicons -->

<link href="<c:url value="/resources/adminltd/css/ionicons.min.css" />"
	rel="stylesheet">

<!-- Theme style -->

<link href="<c:url value="/resources/adminltd/css/AdminLTE.min.css" />"
	rel="stylesheet">

<!-- iCheck -->


<link href="<c:url value="/resources/adminltd/css/blue.css" />"
	rel="stylesheet">


<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

<!-- Google Font -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition lockscreen">
	<!-- Automatic element centering -->
	<div class="lockscreen-wrapper">
		<div class="lockscreen-logo">
			<a href="../../index2.html"><b>Nepal Telecom</b>FTTH CMS</a>
		</div>
		<!-- User name -->
		<div class="lockscreen-name">Reset Password</div>

		<!-- START LOCK SCREEN ITEM -->
		<div class="lockscreen-item">
			<!-- lockscreen image -->
			<div class="lockscreen-image">
				<img
					src="https://doorsanchar.com/wp-content/uploads/2015/07/ftth_logo.jpg"
					alt="User Image">
			</div>
			<!-- /.lockscreen-image -->

			<!-- lockscreen credentials (contains the form) -->
			<form action="<c:url value="/generateToken" />" method="post"
				class="lockscreen-credentials" acceptCharset="UTF-8">

				<div class="input-group">
					<c:if test="${resettype == 'username' || resetuser == 'ERROR'}">
						<input type="text" name="username" class="form-control"
							placeholder="Enter Username">
					</c:if>

					<c:if test="${resetuser == 'TOKENERROR'}">
						<input type="text" name="username" class="form-control"
							placeholder="Enter Username" value="${username}" readonly>
						<input type="text" name="tokenid" class="form-control"
							placeholder="Enter Token ID">
					</c:if>


					<c:if test="${resetuser == 'successtoken'}">
						<input type="text" name="username" class="form-control"
							placeholder="Enter Username" value="${username}" readonly>
						<input type="text" name="tokenid" class="form-control"
							placeholder="Enter Token ID">
					</c:if>

					<div class="input-group-btn">
						<c:if test="${resettype == 'mobile'}">
							<button type="submit" type="submit" name="resettype"
								value="gettoken" class="btn">
								<i class="fa fa-arrow-right text-muted"></i>
							</button>
						</c:if>
						<c:if test="${resettype == 'username' || resetuser == 'ERROR' }">
							<button type="submit" type="submit" name="resettype"
								value="gentoken" class="btn">
								<i class="fa fa-arrow-right text-muted"></i>
							</button>
						</c:if>
						<c:if test="${resetuser == 'successtoken'}">
							<button type="submit" type="submit" name="resettype"
								value="valtokenid" class="btn">
								<i class="fa fa-arrow-right text-muted"></i>
							</button>
						</c:if>
						<c:if test="${resetuser == 'TOKENERROR'}">
							<button type="submit" type="submit" name="resettype"
								value="valtokenid" class="btn">
								<i class="fa fa-arrow-right text-muted"></i>
							</button>
						</c:if>




					</div>

				</div>
			<c:if test="${resetuser == 'TOKENERROR'}">
							<button type="submit" type="submit" name="reseterror"
						value="gentoken" class="btn bg-purple pull-left">
						Resend OTP <i class="fa fa-arrow-right text-muted"></i>
					</button>
				</c:if>	
				
			</form>
			<!-- /.lockscreen credentials -->

		</div>
		<!-- /.lockscreen-item -->
		<div class="help-block text-center">
			<c:if test="${resettype == 'username'}">
				<div class="alert alert-info alert-dismissible">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">×</button>
					<i class="icon fa fa-info"></i> Enter your username to retrieve
					your session
				</div>
			</c:if>
			<c:if test="${Error != null}">
				<div class="alert alert-warning alert-dismissible">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">×</button>
					<i class="icon fa fa-warning"></i> ${Error}
				</div>
			</c:if>
			<c:if test="${Sucess != null}">
				<div class="alert alert-success alert-dismissible">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">×</button>
					<i class="icon fa fa-check"></i> Alert!${Sucess}
				</div>

			</c:if>

		</div>


		<div class="text-center">
			<a href="login.html">Go Back To Login Page</a>
		</div>
		<div class="lockscreen-footer text-center">
			Copyright © 2020-2021 <b><a href="https://ntc.net.np"
				class="text-black">Nepal Telecom</a></b><br> All rights reserved
		</div>
	</div>
	<!-- /.center -->

	<script src="<c:url value="/resources/adminltd/js/jquery.min.js" />"></script>
	<!-- Bootstrap 3.3.7 -->
	<script src="<c:url value="/resources/adminltd/js/bootstrap.min.js" />"></script>
	<!-- iCheck -->
	<script src="<c:url value="/resources/adminltd/js/icheck.min.js" />"></script>

</body>


</html>
