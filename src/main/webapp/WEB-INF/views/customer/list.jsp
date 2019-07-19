<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>${fx}</h1>

<c:forEach var="user" items="${data_list}">
     <h3>${user.getUserName()}</h3>   
</c:forEach>
 

</body>
</html>
