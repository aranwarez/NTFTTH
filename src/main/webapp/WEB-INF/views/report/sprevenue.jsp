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
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Report</a></li>
                        <li class="active">VAS Revenue</li>
                    </ol>

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


                                <div class="overlay">
                                    <i class="fa fa-refresh fa-spin"></i>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body table-responsive">
                                    <form method="post">
                                        <table class="table-condensed">
                                            <tr>
                                                <c:forEach var="DAT" items="${Date_list}">
                                                    <td>From Year</td>
                                                    <td><input style="width: 80px;" class="form-control"
                                                               value="${DAT.CUR_YEAR}" type="number" name="FRM_YEAR"
                                                               id="FRM_YEAR" placeholder="Enter from Year"></td>
                                                    <td>
                                                        <select style="width: 100px;" name="FRM_MONTH"
                                                                id="FRM_MONTH">
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
                                                        </select>
                                                    </td>
                                                </c:forEach>
                                                <c:forEach var="DAT" items="${Date_list}">
                                                    <td>To Year</td>
                                                    <td><input style="width: 80px;" class="form-control"
                                                               value="${DAT.CUR_YEAR}" type="number" name="TO_YEAR"
                                                               id="FRM_YEAR" placeholder="Enter to Year"></td>
                                                    <td>
                                                        <select style="width: 100px;" name="TO_MONTH"
                                                                id="TO_MONTH">
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
                                                        </select>
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                            <tr>
                                                <td>Service Provider</td>
                                                <td><select style="width: 400px;" name="SP_CODE"
                                                            id="SP_CODE">
                                                        <option value=''>Select :</option>
                                                        <c:forEach var="SP" items="${Sp_list}">
                                                            <option value="${SP.SP_CODE}">${SP.SP_NAME}
                                                                ${SP.SP_CODE}</option>
                                                            </c:forEach>
                                                    </select></td>
                                            </tr>
                                            <tr><td>Report Name</td>
                                                <td>
                                                    <select name="reportname">
                                                        <option value="VasRevenueRep">Overall VAS Revenue</option>
                                                        <option value="VasRevenueExceptCSIRep">Overall Revenue Except Cash Sale Items</option>
                                                        <option value="RevenueCSIRep">Cash Sales Revenue</option>
                                                        <option value="SPwiseVasRevenueRep">Service Provider wise Overall VAS Revenue</option>
                                                        <option value="ServicewiseSPVasRevenueRep">Service wise Revenue Report</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                            <tr><td>Export Type</td><td><select name="reporttype"><option value="XLS">XLS</option>
                                                        <option value="pdf">PDF</option>
                                                    </select></td><td></td></tr>
                                            <tr>
                                                <td><button class="btn btn-default" type="submit" formaction="../ReportView" formtarget="_blank">View
                                                    </button>
                                                    <button class="btn btn-default"type="submit" formaction="../Report">Export
                                                    </button></td>
                                            </tr>
                                        </table>
                                    </form>
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

        <%--         <jsp:include page="${request.contextPath}/cashsale/dialog"></jsp:include> --%>


        <jsp:include page="${request.contextPath}/footJS"></jsp:include>
        <script src="<c:url value="/resources/function/Report/balance.js" />"></script>
        <script src="<c:url value="/resources/adminltd/js/commonajax.js" />"></script>


    </body>
</html>
