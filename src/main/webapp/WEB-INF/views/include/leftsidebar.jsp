<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


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


<!-- <li class="treeview"><a href="#"> <i class="fa fa-folder"></i> -->
<!-- 		<span>VAS</span> <span class="pull-right-container"> <i -->
<!-- 			class="fa fa-angle-left pull-right"></i> -->
<!-- 	</span> -->
<!-- </a> -->
<!-- 	<ul class="treeview-menu"> -->
<!-- 		<li><a href="../examples/invoice.html"><i -->
<!-- 				class="fa fa-circle-o"></i> Invoice</a></li> -->
<!-- 	</ul></li> -->