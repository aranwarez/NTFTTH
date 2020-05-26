<%@page session="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="home" value="/" scope="request" />

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Nepal Telecom | ${fx}</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<jsp:include page="${request.contextPath}/headCss"></jsp:include>

</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<header class="main-header">
			<jsp:include page="${request.contextPath}/topmenu"></jsp:include>
		</header>
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar">
			<!-- search form -->
		
			<!-- /.search form -->

			<jsp:include page="${request.contextPath}/leftmenu"></jsp:include>

		</aside>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>${fx}</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li><a href="#">Tables</a></li>
					<li class="active">Data tables</li>

				</ol>

			</section>

			<!-- Main content -->
			<section class="content">
Enter Service No.
 <div class="row">

<form class="login-form" action="<c:url value="/ticket-history/fetch" />" method="GET"
				acceptCharset="UTF-8" >
				 					
					<div class="col-md-3">					

								<input type="text" id="MAIN_SRV_NO" <c:if test="${srv_no != null}"> value="${srv_no}"
											
										</c:if>
									class="form-control" name="srv_no" placeholder="Enter Service No">
							 
					</div>

					<div class="col-md-3">
						<div class="align-middle">
						
								<input type="submit"  class="btn btn-primary" value="View">
						
						</div>
					</div>
    
    </form>
    				<div class="col-xs-12">
						<!-- /.box -->

						<div class="box">
							<div class="box-header">
								<h3 class="box-title">${fx}</h3>
							</div>
	                   <div class="overlay">
								<i class="fa fa-refresh fa-spin"></i>
							</div>


							<div class="box-body table-responsive no-padding">


								<table id="example1" class="table table-bordered table-striped">
									<thead>
										<tr>
									     	<td>SRV.NO</td>
											<th>OFFICE</th>
											<th>OLT</th>
											<th>FDC</th>
											<th>COMPLAIN_NO</th>											
											<th>SERVICE_NO</th>
											<th>FROM_TEAM</th>
											<th>TO_TEAM</th>
											<th>FLAG</th>
											<th>REMARKS</th>
											<th>Create By</th>
											<th>Date</th>
											<th>CPE_RX_LVL</th>
										


								</tr>
									</thead>
									
									<tbody>
									
									<c:forEach var="user" items="${data_list}">
									
									<tr>
									<td>${user.MAIN_SRV_NO}</td>
									<td>${user.REGION}(${user.OFFICE})</td>
									<td>${user.OLT}</td>
									<td>${user.FDC}</td>
									<td>${user.CONTACT_NAME}(${user.COMPLAIN_NO})</td>									
									<td>${user.MASTER_SERVICE_NO}</td>								
									<td>${user.FROM_SUB_TEAM_CODE} </td>
									<td>${user.TO_SUB_TEAM_CODE}</td>
									<td>${user.SOLVE_FLAG}</td>
									<td>${user.REMARKS}</td>
									<td>${user.CREATE_BY} </td>
									<td>${user.CREATE_DT}</td>
									<td>${user.CPE_RX_LVL}</td> 
										
									</tr>
									
										</c:forEach>
									</tbody>
								</table>



							</div>




						</div>
						<!-- /.box -->
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<footer class="main-footer">
			<jsp:include page="${request.contextPath}/footer"></jsp:include>
		</footer>

		<!-- Control Sidebar -->
		<aside class="control-sidebar control-sidebar-dark">
			<!-- Create the tabs -->
			<ul class="nav nav-tabs nav-justified control-sidebar-tabs">
				<li><a href="#control-sidebar-home-tab" data-toggle="tab"><i
						class="fa fa-home"></i></a></li>
				<li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i
						class="fa fa-gears"></i></a></li>
			</ul>

		</aside>
		<!-- /.control-sidebar -->
		<!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->

	<jsp:include page="${request.contextPath}/troubleticket/dialog"></jsp:include>
	<jsp:include page="${request.contextPath}/footJS"></jsp:include>



	<script
		src="<c:url value="/resources/function/Complain/history.js?verdt=521" />"></script>
	<script src="<c:url value="/resources/adminltd/js/commonajax.js" />"></script>

	<script>
		$(function() {

			$('#example1').DataTable({
				"bDestroy" : true
			})
			$("#REGION_CODE,#ZONE_CODE,#DISTRICT_CODE,#OFFICE_CODE,#USER_ID")
					.select2();

			var level = '<c:out value="${USER_LEVEL}"/>';
			

			$('#QFROM_DT').nepaliDatePicker();
			$('#QTO_DT').nepaliDatePicker();
		})
	</script>
</body>
</html>
