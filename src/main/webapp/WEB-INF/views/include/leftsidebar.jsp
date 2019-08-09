<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<c:set var="leftmenuMODULE_ACCESS" value="${leftmenuMODULE_ACCESS}" />

new.
<c:forEach var="Aleftmenu" items="${leftmenuheadlist}">

	<li class="treeview"><c:choose>
			<c:when
				test="${Aleftmenu.getMODULE_TYPE() == 'P'} && ((${leftmenuMODULE_ACCESS}=='B2') || (${leftmenuMODULE_ACCESS}=='P'))">



				<a href="#"
					onclick="return getSubMenu('${Aleftmenu.getSN()}', '${Aleftmenu.getMENU_CODE()}', '${leftmenuRoleCode}')">
					<i class="fa fa-folder"></i> <span>${Aleftmenu.getMENU_DESC()}</span>
					<span class="pull-right-container"> <i
						class="fa fa-angle-left pull-right"></i>
				</span>
				</a>
/.end
	</c:when>

		</c:choose>

		<ul class="treeview-menu">

			<li><a href="../examples/invoice.html"><i
					class="fa fa-circle-o"></i> ${Aleftmenu.getMENU_DESC()}</a></li>

		</ul></li>







</c:forEach>


<c:forEach var="Aleftmenu" items="${leftmenuheadlist}">
	<c:if
		test="${Aleftmenu.getMODULE_TYPE() == 'P'} && ((${leftmenuMODULE_ACCESS}=='B2') || (${leftmenuMODULE_ACCESS}=='P'))"/>

		<li class="treeview"><a href="#"> <i class="fa fa-folder"></i>
				
				<span>${Aleftmenu.getMENU_DESC()}</span>
				
				
				 <span
				class="pull-right-container"> <i
					class="fa fa-angle-left pull-right"></i>
			</span>
		</a>
			<ul class="treeview-menu">

				<li><a href="../examples/invoice.html"><i
						class="fa fa-circle-o"></i> Invoice</a></li>

			</ul>
			</li>

	<c:if
		test="${Aleftmenu.getMODULE_TYPE() == 'C'} && ((${leftmenuMODULE_ACCESS}=='B1') || (${leftmenuMODULE_ACCESS}=='C'))"/>
		<li class="treeview"><a href="#"> <i class="fa fa-folder"></i>
				<span>${Aleftmenu.getMENU_DESC()}</span> <span
				class="pull-right-container"> <i
					class="fa fa-angle-left pull-right"></i>
			</span>
		</a>
			<ul class="treeview-menu">

				<li><a href="../examples/invoice.html"><i
						class="fa fa-circle-o"></i> Invoice</a></li>

			</ul></li>
	



</c:forEach>

<li class="treeview"><a href="#"> <i class="fa fa-folder"></i>
		<span>VAS</span> <span class="pull-right-container"> <i
			class="fa fa-angle-left pull-right"></i>
	</span>
</a>
	<ul class="treeview-menu">
		<li><a href="../examples/invoice.html"><i
				class="fa fa-circle-o"></i> Invoice</a></li>
	</ul></li>






