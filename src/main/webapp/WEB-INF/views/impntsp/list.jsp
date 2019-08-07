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
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- Sidebar user panel -->
				<div class="user-panel">
					<div class="pull-left image">
						<img
							src="<c:url value="/resources/adminltd/dist/img/user2-160x160.jpg" />"
							class="img-circle" alt="User Image">
					</div>
					<div class="pull-left info">
						<p>NABIN</p>
						<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
					</div>
				</div>
				<!-- search form -->
				<form action="#" method="get" class="sidebar-form">
					<div class="input-group">
						<input type="text" name="q" class="form-control"
							placeholder="Search..."> <span class="input-group-btn">
							<button type="submit" name="search" id="search-btn"
								class="btn btn-flat">
								<i class="fa fa-search"></i>
							</button>
						</span>
					</div>
				</form>
				<!-- /.search form -->
				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="sidebar-menu" data-widget="tree">
					<li class="header">MAIN NAVIGATION</li>
					<jsp:include page="${request.contextPath}/leftmenu"></jsp:include>
				</ul>
			</section>
			<!-- /.sidebar -->
		</aside>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>Upload Execl File</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li><a href="#">Tables</a></li>
					<li class="active">Data tables</li>
				</ol>
				<br /> <br />
				<table>
					<tr>
						<c:forEach var="DAT" items="${Date_list}">
							<td>Year</td>
							<td><input style="width: 80px;" class="form-control"
								value="${DAT.CUR_YEAR}" type="number" name="QIMP_YEAR"
								id="QIMP_YEAR" placeholder="Enter Year"></td>
							<td>Period</td>
							<td><select style="width: 70px;" class="form-control"
								name="QIMP_PERIOD" id="QIMP_PERIOD">
									<c:forEach begin="1" end="4" varStatus="loop">
										<c:set var="indexcat" value="0${loop.index}" />
										<c:if test="${indexcat==DAT.CUR_PERIOD}">
											<option selected value='0${loop.index}'>${loop.index}</option>
										</c:if>
										<c:if test="${indexcat!=DAT.CUR_PERIOD}">
											<option value='0${loop.index}'>${loop.index}</option>
										</c:if>
									</c:forEach>
							</select></td>
							<td><select style="width: 100px;" name="QIMP_MONTH"
								id="QIMP_MONTH">
									<option value=''>Select :</option>
									<c:forEach var="MONTH" items="${Mon_list}">
										<c:if test="${DAT.CUR_MONTH==MONTH.MONTH_CD}">
											<option selected value="${MONTH.MONTH_CD}">
												${MONTH.NEP_MONTH}</option>
										</c:if>
										<c:if test="${DAT.CUR_MONTH!=MONTH.MONTH_CD}">
											<option value="${MONTH.MONTH_CD}">
												${MONTH.NEP_MONTH}</option>
										</c:if>
									</c:forEach>
							</select></td>
						</c:forEach>
						<td>VAS Services</td>
						<td><select style="width: 200px;" name="QSERVICE_CODE"
							id="QSERVICE_CODE">
								<option value=''>Select :</option>
								<c:forEach var="VASSER" items="${VASSer_list}">
									<option value="${VASSER.SERVICE_CODE}">
										${VASSER.SERVICE_CODE}</option>
								</c:forEach>
						</select></td>
						<td>NT/SP</td>
						<td><select style="width: 80px;" class="form-control"
							name="QNT_SP" id="QNT_SP">
								<option value='NT'>NT</option>
								<option value='SP'>SP</option>
						</select></td>
						<td>Post</td>
						<td><select style="width: 80px;" class="form-control"
							name="QPOST_FLAG" id="QPOST_FLAG">
								<option value='N'>N</option>
								<option value='Y'>Y</option>
								<option value=''>All</option>
						</select></td>
						<td><input type="file" id="my_file_input"
							accept="application/vnd.ms-excel" /></td>
					</tr>
					<tr>
						<td><input type="button" value="Query"
							onclick="getImpNtspFilterList();"></td>
						<td><input type="button" value="Import Data"
							onclick="importdata();"></td>
						<td><input type="button" value="Post"
							onclick="postExcelTransaction();"></td>
						<td><input type="button" value="Delete"
							onclick="getCpFilterList();"></td>
					</tr>
				</table>

				<a href="#" class="btn btn-primary pull-right" data-toggle="modal"
					data-target="#myModal"> <i class="fa fa-plus"></i> Add
				</a>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-xs-12">

						<!-- /.box -->

						<div class="box">
							<div class="box-header">
								<h3 class="box-title">${fx}</h3>
							</div>

							<%
								if (request.getParameter("sucess") != null) {
							%>>
							<div class="alert alert-success">
								<strong> <%=request.getParameter("sucess")%>
								</strong>
							</div>
							<%
								}
							%>
							<%
								if (request.getParameter("error") != null) {
							%>>
							<div class="alert alert-danger">
								<strong> <%=request.getParameter("error")%>
								</strong>
							</div>
							<%
								}
							%>

							<!-- /.box-header -->
							<div class="box-body table-responsive">
								<table border="1" id='my_file_output'>
									<thead>
										<tr></tr>
									</thead>
									<tbody></tbody>

								</table>



								<table id="example1" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>TransNo</th>
											<th>Seq.No.</th>
											<th>Year</th>
											<th>Period</th>
											<th>Month</th>
											<th>Service</th>
											<th>NT/SP</th>
											<th>Category</th>
											<th>ESME Imp.</th>
											<th>Sp Code</th>
											<th>ESME</th>
											<th>Rate</th>
											<th>StartDt</th>
											<th>MO</th>
											<th>MT</th>
											<th>Edit</th>
											<th>Delete</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="user" items="${data_list}">
											<tr>
												<td>${user.TRANS_NO}</td>
												<td>${user.SEQ_NO}</td>
												<td>${user.IMP_YEAR}</td>
												<td>${user.IMP_PERIOD}</td>
												<td>${user.IMP_MONTH}</td>
												<td>${user.SERVICE_CODE}</td>
												<td>${user.NT_SP}</td>
												<td>${user.CATEGORY}</td>
												<td>${user.CP_DESC}</td>
												<td>${user.S_NO}</td>
												<td>${user.ESME_CODE}</td>
												<td>${user.RATE}</td>
												<td>${user.START_DT}</td>
												<td>${user.MO_1ST}</td>
												<td>${user.MT_1ST}</td>
												<td>
													<div class="btn-group">
														<a href="#" class="btn btn-info" data-toggle="modal"
															data-target="#editModal"
															onclick="return editCp('${user.CP_CODE}')"> <i
															class="fa fa-edit"></i> Edit
														</a>
													</div>
												</td>
												<td>
													<div>
														<a href="" class="btn btn-default" data-toggle="modal"
															data-target="#deleteModal"
															onclick="return deleteCp('${user.CP_CODE}')"> <i
															class="fa fa-trash"></i> Delete
														</a>
													</div>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!-- /.box-body -->
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

	<jsp:include page="${request.contextPath}/dialogimpntsp"></jsp:include>

	<jsp:include page="${request.contextPath}/footJS"></jsp:include>

	<script src="<c:url value="/resources/adminltd/js/xlsx.js" />"></script>

	<script>
		$(function() {

			$('#example1').DataTable({
				"iDisplayLength" : 100
			});

		})
	</script>
	<script src="<c:url value="/resources/function/impntsp.js?v=1.1" />"></script>



</body>
</html>
