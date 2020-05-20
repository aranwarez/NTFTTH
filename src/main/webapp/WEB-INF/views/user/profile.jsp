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
<style>
.example-modal .modal {
	position: relative;
	top: auto;
	bottom: auto;
	right: auto;
	left: auto;
	display: block;
	z-index: 1;
}

.example-modal .modal {
	background: transparent !important;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<header class="main-header">
			<jsp:include page="${request.contextPath}/topmenu"></jsp:include>
		</header>
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar">
			
					<jsp:include page="${request.contextPath}/leftmenu"></jsp:include>
				
				
		</aside>

	
	
	<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
         PROFILE
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">${name}</a></li>
        <li class="active"> profile</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">

      <div class="row">
        <div class="col-md-3">

          <!-- Profile Image -->
          <div class="box box-primary">
            <div class="box-body box-profile">
              <img class="profile-user-img img-responsive img-circle" src="<c:url value="/resources/adminltd/dist/img/avatar5.png" />" alt="User profile picture">
	
	
			<c:forEach var="user" items="${userinfo}">
              <h3 class="profile-username text-center">${user.FULL_NAME}</h3> 
               <p class="text-muted text-center">${user.USER_ID}(${user.REGION})</p>
             
		
              <ul class="list-group list-group-unbordered">
                <li class="list-group-item">
                  <b>Zone</b> <a class="pull-right">${user.ZONE}</a>                  
                </li>
                <li class="list-group-item">
                 <b>DISTRICT</b> <a class="pull-right">${user.DISTRICT}</a>
                 </li>
				 <li class="list-group-item">
                 <b>OFFICE</b> <a class="pull-right">${user.OFFICE}</a>
                 </li>
               
               <li class="list-group-item">
                 <b>EMP.NAME</b> <a class="pull-right">${user.EMPLOYEE_NAME}</a>
                 </li>
             
               <li class="list-group-item">
                 <b>LOCK_FLAG</b> <a class="pull-right">${user.LOCK_FLAG}</a>
                 </li>
           
              <li class="list-group-item">
                 <b>SUPER_FLAG</b> <a class="pull-right">${user.SUPER_FLAG}</a>
                 </li>
 
              <li class="list-group-item">
                 <b>DISABLE_FLAG</b> <a class="pull-right">${user.DISABLE_FLAG}</a>
                 </li>
              
              <li class="list-group-item">
                 <b>USER_LEVEL</b> <a class="pull-right">
                 <c:if test="${user.USER_LEVEL=='1'}"> SUPER </c:if>    
                  <c:if test="${user.USER_LEVEL=='2'}"> Region </c:if>    
                   <c:if test="${user.USER_LEVEL=='3'}"> Zone </c:if>    
                    <c:if test="${user.USER_LEVEL=='4'}"> District </c:if>    
                     <c:if test="${user.USER_LEVEL=='5'}"> Office </c:if>    
                      <c:if test="${user.USER_LEVEL=='6'}">  Customize Office</c:if>    
                 
                 </a>
                 </li> 
             
              <li class="list-group-item">
                 <b>ROLE_CODE</b> <a class="pull-right">${user.ROLE_CODE}</a>
                 </li>              
             
              <li class="list-group-item">
                 <b>MOBILE_NO</b> <a class="pull-right">${user.MOBILE_NO}</a>
                 </li>
              
              </ul>
</c:forEach>
              <a  class="btn btn-primary btn-block"><b>Every day is a good day</b></a>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->

          
        </div>
        <!-- /.col -->
        <div class="col-md-9">
          <div class="nav-tabs-custom">
            <ul class="nav nav-tabs">
            
           <!--    <li ><a href="#activity" data-toggle="tab">--</a></li>-->
           <li class="active"><a href="#settings" data-toggle="tab">Settings</a></li>
              <li><a href="#timeline" data-toggle="tab">Password Change</a></li>
              
            </ul>
            <div class="tab-content">
              <div class="tab-pane" id="activity">
                <!-- Post -->
                activity
                  <!-- /.post -->
              </div>
              <!-- /.tab-pane -->
              <div class="tab-pane" id="timeline">
                <!-- The timeline -->
               
               
               
               <form class="form-horizontal" name="myForm"  method="POST" action="#" onsubmit="return validateForm()" >
                <c:forEach var="user" items="${userinfo}">
                  <div class="form-group">
                 
                  <input type="hidden" id="CUSER_ID" name="CUSER_ID" value="${user.USER_ID}">
                    <label for="inputName" class="col-sm-2 control-label">Old Password</label>

                    <div class="col-sm-10">
  							<input type="password" class="form-control"  id="myOldPassword" onchange="return OldPassword()">
                            <span id="oldpass"></span>                      
							</div>
                  </div>
                  <div class="form-group">
                    <label for="pass" class="col-sm-2 control-label">New Password</label>

                    <div class="col-sm-10">
                      <input type="password" class="form-control" id="pass">
                    </div>
                  </div>
                  
                      <div class="form-group">
                    <label for="pass" class="col-sm-2 control-label">Re- Password</label>

                    <div class="col-sm-10">
                      <input type="password" class="form-control" id="cpassmatch">
                    </div>
                  </div>
                  
                  </c:forEach>
				
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                      <button type="button" class="btn btn-danger" onclick="return changePassword()">Submit</button>
                    </div>
                  </div>
                </form>
                
              </div>
              <!-- /.tab-pane -->

              <div class="active tab-pane" id="settings">
              
				
				
                <form class="form-horizontal" name="myForm"  method="POST" action="#" onsubmit="return validateForm()" >
                <c:forEach var="user" items="${userinfo}">
                  <div class="form-group">
                 
                  <input type="hidden" id="UUSER_ID" name="UUSER_ID" value="${user.USER_ID}">
                    <label for="FULL_NAME" class="col-sm-2 control-label">FULL_NAME</label>

                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="UFULL_NAME" id="UFULL_NAME" value="${user.FULL_NAME}" placeholder="FULL_NAME">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="UMOBILE_NO" class="col-sm-2 control-label">MOBILE_NO</label>

                    <div class="col-sm-10">
                      <input type="text" class="form-control" name="UMOBILE_NO" value="${user.MOBILE_NO}" id="UMOBILE_NO" placeholder="MOBILE_NO">
                    </div>
                  </div>
                  </c:forEach>
                  
                  
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                      <div class="checkbox">
                        <label>
                          <input type="checkbox" required="required"> I agree to the <a href="#">terms and conditions</a>
                        </label>
                      </div>
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                      <button type="submit" class="btn btn-danger" onsubmit="return validateForm()">Submit</button>
                    </div>
                  </div>
                </form>
                
              </div>
              <!-- /.tab-pane -->
            </div>
            <!-- /.tab-content -->
          </div>
          <!-- /.nav-tabs-custom -->
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

	<jsp:include page="${request.contextPath}/dialoguser?a=1"></jsp:include>
	<jsp:include page="${request.contextPath}/footJS"></jsp:include>

	<script>
		$(function() {
		
			 $('#userlist').DataTable( {
			        "scrollX": true
			    } );
			 $("#EMPLOYEE_CODE,#EDITEMPLOYEE_CODE,#EDITOFFICE_CODE,#OFFICE_CODE").select2();
			 
			 $('select:not(.normal)').each(function () {
	                $(this).select2({
	                    dropdownParent: $(this).parent()
	                });
	            });
			
		})
	</script>
	<script src="<c:url value="/resources/function/myprofile.js?a=156" />"></script>

</body>
</html>
