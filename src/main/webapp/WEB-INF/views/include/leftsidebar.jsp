<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<c:set var="leftmenuMODULE_ACCESS" value="${leftmenuMODULE_ACCESS}" />

<c:forEach var="Aleftmenu" items="${leftmenuheadlist}">

	<li class="treeview"><a href="#"
		onclick="return getSubMenu('${Aleftmenu.getSN()}', '${Aleftmenu.getMENU_CODE()}', '${leftmenuRoleCode}')">
			<i class="fa fa-folder"></i> <span>${Aleftmenu.getMENU_DESC()}</span>
			<span class="pull-right-container"> <i
				class="fa fa-angle-left pull-right"></i>
		</span>
	</a>

		<ul class="treeview-menu" id="details${Aleftmenu.getSN()}">
		</ul></li>


</c:forEach>

<script>
	var IsLoggedIn = new Boolean(false);
	function getSubMenu(sn, PARENT_MENU, ROLE_CODE) {
		jQuery.ajaxSetup({
			async : false
		});
		var array = {};
		var innerHtml;

		$("#details" + sn).html('');
		$
				.get(
						'../getChildMenulist',
						{
							getChildlist : "getChildlist",
							PARENT_MENU : PARENT_MENU,
							ROLE_CODE : ROLE_CODE
						},
						function(response) {
							//  alert(JSON.stringify(response));

							$
									.each(
											response,
											function(index, value) {

												innerHtml = "<li><a  href=" + value.menu_URL + "><i class='fa fa-circle-o'></i>"
														+ value.menu_DESC
														+ " </a></li>";
												if (value.menu_CODE == 'M0003001'
														|| value.menu_CODE == 'SUPER002') {
													// alert('sadsad');
													innerHtml = "<li><a  href=" + value.menu_URL + " target='_blank'><i class='fa fa-circle-o'></i>"
															+ value.menu_DESC
															+ " </a></li>";
												}

												$("#details" + sn).append(
														innerHtml);

											});

						});

	}
</script>


<!-- <li class="treeview"><a href="#"> <i class="fa fa-folder"></i> -->
<!-- 		<span>VAS</span> <span class="pull-right-container"> <i -->
<!-- 			class="fa fa-angle-left pull-right"></i> -->
<!-- 	</span> -->
<!-- </a> -->
<!-- 	<ul class="treeview-menu"> -->
<!-- 		<li><a href="../examples/invoice.html"><i -->
<!-- 				class="fa fa-circle-o"></i> Invoice</a></li> -->
<!-- 	</ul></li> -->