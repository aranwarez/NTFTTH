<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- Sidebar user panel -->
				<div class="user-panel">
					<div class="pull-left image">
						<img
							src="<c:url value="/resources/adminltd/dist/img/avatar5.png" />"
							class="img-circle" alt="User Image">
					</div>
					<div class="pull-left info">
						<p>${UserList.getUSER_ID()}</p>
						<a href="../my-profile/list"><i class="fa fa-circle text-success"></i> Online</a>
					</div>
				</div>
				
				<!-- search form -->
					<!--<form action="#" method="get" class="sidebar-form">
					<div class="input-group">
						<input type="text" name="q" class="form-control"
							placeholder="Search..."> <span class="input-group-btn">
							<button type="submit" name="search" id="search-btn"
								class="btn btn-flat">
								<i class="fa fa-search"></i>
							</button>
						</span>
					</div>
				</form>-->
				<!-- /.search form -->
				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="sidebar-menu" data-widget="tree">
					<li class="header">MAIN NAVIGATION</li>
					
<c:set var="leftmenuMODULE_ACCESS" value="${leftmenuMODULE_ACCESS}" />

<c:forEach var="Aleftmenu" items="${leftmenuheadlist}">
	<c:if test="${Aleftmenu.getPARENT_MENU()==null}">
		<li class="treeview"><a href="#"> <i
				class="fa fa-${Aleftmenu.getICON()}"></i> <span>${Aleftmenu.getMENU_DESC()}</span>
				<span class="pull-right-container"> <i
					class="fa fa-angle-left pull-right"></i>
			</span>
		</a>

			<ul class="treeview-menu">
				<c:forEach var="detailmenu" items="${leftmenuheadlist}">
				<c:if test="${Aleftmenu.getMENU_CODE()==detailmenu.getPARENT_MENU()}">
				<li><a href="${detailmenu.getMENU_URL()}"><i
								class='fa fa-${detailmenu.getICON()}'></i><span>${detailmenu.getMENU_DESC()}
							</span></a></li>
				
				</c:if>
				</c:forEach>



			</ul></li>

	</c:if>
</c:forEach>
	</ul>
</section>
			<!-- /.sidebar -->


<!-- <li class="treeview"><a href="#"> <i class="fa fa-folder"></i> -->
<!-- 		<span>VAS</span> <span class="pull-right-container"> <i -->
<!-- 			class="fa fa-angle-left pull-right"></i> -->
<!-- 	</span> -->
<!-- </a> -->
<!-- 	<ul class="treeview-menu"> -->
<!-- 		<li><a href="../examples/invoice.html"><i -->
<!-- 				class="fa fa-circle-o"></i> Invoice</a></li> -->
<!-- 	</ul></li> -->