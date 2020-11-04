<%--
  Created by IntelliJ IDEA.
  User: HP Omen 15
  Date: 04.11.2020
  Time: 9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    session.removeAttribute("user");
    response.sendRedirect("index.jsp");
%>
<html>
<head>
    <title>Library logout</title>
</head>
<body>

</body>
</html>
