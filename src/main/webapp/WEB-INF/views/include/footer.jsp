<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
   <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <c:set var="today" value="<%=new java.util.Date()%>" />
    
    	<div class="pull-right hidden-xs">
				<b>Version</b> ${version}
			</div>
			<strong>Copyright &copy; <fmt:formatDate pattern="yyyy-MM-dd" value="${today}" /> <a
				href="${website}" target="_BLANK">Nepal Telecom</a>.
			</strong> All rights reserved.
			
			 