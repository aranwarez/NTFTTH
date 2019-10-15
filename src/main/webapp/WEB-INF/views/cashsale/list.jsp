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
				<h1>${fx}</h1>
				<ol class="breadcrumb">

					<li><a href="list" class="btn bg-red"> <i
							class="fa fa-backward"></i> BACK
					</a></li>

				</ol>
				<br />
				<div style="margin: 0;" class="box">

					<table class="table table-condensed">
						<tr>
							<td><label>Trans No.</label></td>
							<td><input readonly type="text" id="transno"></td>
							<td><label>Customer No.</label></td>
							<td><select style="width: 400px;" name="QSP_CODE"
								id="SP_CODE" onchange="getcustomerinfo(this.value)">
									<option value=''>Select :</option>
									<c:forEach var="SP" items="${Sp_list}">
										<option value="${SP.SP_CODE}">${SP.SP_NAME}
											${SP.SP_CODE}</option>
									</c:forEach>
							</select></td>
							<td><label>Date : </label> <c:forEach var="DAT"
									items="${Date_list}">
									<input type="text" id="nepdate" value="${DAT.NEP_TODAY_DATE}"
										class="nepali-calendar">
								</c:forEach></td>
						</tr>
						<tr>
							<td><label>Pan No.</label></td>
							<td id="panno"></td>
							<td><label>Address</label></td>
							<td id="address"></td>

						</tr>
					</table>
				</div>

			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-xs-12">

						<!-- /.box -->

						<div class="box">
							<!-- 							<div class="box-header"> -->
							<%-- 								<h3 class="box-title">${fx}</h3> --%>
							<!-- 							</div> -->

							<div class="overlay">
								<i class="fa fa-refresh fa-spin"></i>
							</div>

							<!-- /.box-header -->
							<div class="box-body table-responsive">
								<table id="example1" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th style="text-align: center"><a href="#"
												class="btn btn-default bg-green" onclick="additem()"> <i
													class="fa fa-plus"></i>
											</a></th>
											<th>Code</th>
											<th>Name</th>
											<th>Quantity</th>
											<th>Rate</th>
											<th>Revenue</th>
											<th>TSC</th>
											<th>VAT</th>
											<th>Total</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th colspan="5">Total</th>
											<th id="sumrev">-</th>

											<th id="sumtsc">-</th>

											<th id="sumvat">-</th>
											<th id="sumtot">-</th>

										</tr>
									</tfoot>

								</table>

							</div>
							<!-- /.box-body -->
							<hr>
							<table class="table-condensed">
								<tr>
									<td>Bank</td>
									<td><select style="width: 400px;" name="BANK_CODE"
										id="BANK_CODE">
											<option value=''>Select :</option>
											<c:forEach var="SP" items="${bank_list}">
												<option value="${SP.BANK_CD}">${SP.BANK_NAME}
													${SP.BANK_CODE}</option>
											</c:forEach>
									</select></td>
									<td>Received Amount</td>
									<td><input style="text-align: right"
										onchange="number2text(this.value)" class="form-control"
										id="AMT" placeholder="Enter Amount" type="number"></td>
								</tr>
								<tr>
									<td><label>Remarks</label></td>
									<td><input type="text" id="remarks" class="form-control"
										placeholder="Enter Remarks"></td>
								</tr>
								<tr>
									<td><label>Amount In Words</label></td>
									<td colspan="3"><input id="inwords" class="form-control"
										type="text" readonly="readonly"></td>
								</tr>

								<tr>

									<td>

										<button id="savebtn" onclick="post()"
											class='btn bg-blue myClickDisabledElm'>Save</button>
									</td>
									<td>


										<button id="printbtn" type="submit" form="my_form" class='btn bg-purple'
											disabled="disabled">Print</button>


									</td>


								</tr>

							</table>
							<form id="my_form" class="form" action="../ReportView" method=post target="_blank">
								<input type="hidden" name="reportname" value="CashSaleReceipt">
								<input type="hidden" name="TRANS_NO" id="hiddentransno">
							</form>
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


	<jsp:include page="${request.contextPath}/footJS"></jsp:include>


	<script src="<c:url value="/resources/function/SalesBill/Bill.js" />"></script>
	<script
		src="<c:url value="/resources/function/SalesBill/Amt2Words.js" />"></script>
	<script src="<c:url value="/resources/adminltd/js/commonajax.js" />"></script>



</body>
</html>
