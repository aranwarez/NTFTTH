<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
   
	<!-- jQuery 3 -->
	<script src="<c:url value="/resources/adminltd/js/jquery.min.js" />"></script>
	<!-- Bootstrap 3.3.7 -->
	<script src="<c:url value="/resources/adminltd/js/bootstrap.min.js" />"></script>
	<!-- DataTables -->
	<script
		src="<c:url value="/resources/adminltd/js/jquery.dataTables.min.js" />"></script>

	<script
		src="<c:url value="/resources/adminltd/js/dataTables.bootstrap.min.js" />"></script>

	<!-- SlimScroll -->
	<script
		src="<c:url value="/resources/adminltd/js/jquery.slimscroll.min.js" />"></script>

	<!-- FastClick -->
	<script src="<c:url value="/resources/adminltd/js/fastclick.js" />"></script>

	<!-- AdminLTE App -->
	<script src="<c:url value="/resources/adminltd/js/adminlte.min.js" />"></script>

	<!-- Nepali DatePicker -->
	<script src="<c:url value="/resources/adminltd/js/nepali.datepicker.js" />"></script>
	
	<!-- Select 2-->
	<script src="<c:url value="/resources/adminltd/js/select2.js" />"></script>
	
	
<script>
(function ($) {
	/* Recover sidebar state */
    (function () {
        if (Boolean(localStorage.getItem('sidebar-toggle-collapsed'))) {
            var body = document.getElementsByTagName('body')[0];
            body.className = body.className + ' sidebar-collapse';
        }
    })();
	
	
    /* Store sidebar state */
    $('.sidebar-toggle').click(function(event) {
        event.preventDefault();
        if (Boolean(localStorage.getItem('sidebar-toggle-collapsed'))) {
            localStorage.setItem('sidebar-toggle-collapsed', '');
        } else {
            localStorage.setItem('sidebar-toggle-collapsed', '1');
        }
    });
})(jQuery);
  
  
</script>
	
	