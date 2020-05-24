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
<body class="hold-transition login-page">

	<div class="login-box">

		<h2 class="login-logo">${fx}</h2>
		<!-- /.login-logo -->
		<div class="login-box-body">

			<p class="login-box-msg"></p>
			<c:choose>
				<c:when test="${not empty error}">
        ${error}
    </c:when>
				<c:otherwise>Sign in to start your session</c:otherwise>
			</c:choose>
			</p>
			<form action="<c:url value="/postLogIn" />" method="post"
				acceptCharset="UTF-8">

				<div class="form-group has-feedback">
					<input type="text" name="USER_ID" class="form-control"
						placeholder="USER" required="required"> <span
						class="glyphicon glyphicon-envelope form-control-feedback"></span>

				</div>

				<div class="form-group has-feedback">
					<input type="password" name="PASSWORD" class="form-control"
						placeholder="Password" required="required"> <span
						class="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>
				<div class="row">

					<!-- /.col -->
					<div class="col-xs-4">
						<button type="submit" class="btn btn-primary btn-block btn-flat">Sign
							In</button>
					</div>
					<!-- /.col -->
				</div>
			</form>
	<button onclick="window.location.href='forgotpassword'"
								class="btn bg-red">Forgot Password</button>
	


		</div>
		<!-- /.login-box-body -->
	</div>
	<!-- /.login-box -->
	<!-- jQuery 3 -->
	<script src="<c:url value="/resources/adminltd/js/jquery.min.js" />"></script>
	<!-- Bootstrap 3.3.7 -->
	<script src="<c:url value="/resources/adminltd/js/bootstrap.min.js" />"></script>
	<!-- iCheck -->
	<script src="<c:url value="/resources/adminltd/js/icheck.min.js" />"></script>
	<script>
		$(function() {
			$('input').iCheck({
				checkboxClass : 'icheckbox_square-blue',
				radioClass : 'iradio_square-blue',
				increaseArea : '20%' /* optional */
			});
		});
	</script>
</body>
</html>
