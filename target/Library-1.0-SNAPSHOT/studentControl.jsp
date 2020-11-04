<%@ page import="Classes.User" %>
<%@ page import="Classes.BorrowedBook" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Classes.DBQueries" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: HP Omen 15
  Date: 04.11.2020
  Time: 11:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || user.isLibrarian() == 0) {
        response.sendRedirect("index.jsp");
    }

    ArrayList<User> studentList = new ArrayList<>();
    try {
        studentList = DBQueries.getUserList();
    } catch (SQLException throwable) {
        throwable.printStackTrace();
    }
%>
<html>
<head>
    <title>Library student control</title>
    <link rel="stylesheet" href="main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
</head>
<body>
<div id="navbar">
    <ul>
        <li><a href="index.jsp">Main</a></li>
        <li><a href="bookControl.jsp">Books control</a></li>
        <li><a href="studentControl.jsp">Students control</a></li>
        <li><a href="borrowControl.jsp">Borrow control</a></li>
        <li><a href="profile.jsp">Profile</a></li>
        <li><a href="login.jsp">Log in</a></li>
        <li><a href="logout.jsp">Log out</a></li>
    </ul>
</div>

<div class="container">
    <%  String result = (String) request.getAttribute("result");
        if (result != null) { %>
    <div id="displayResult" class="alert alert-primary m-2" role="alert">
        <h3><%=result%></h3>
    </div>
    <script type="text/javascript">
        $("#displayResult").show().delay(5000).fadeOut();
    </script>
    <%  } %>
    <%
        for (User student : studentList) { %>
    <form id="student-<%=student.getId()%>" action="studentServlet" method="post">
        <div class="row m-2 border border-primary">
            <div class="column p-2">
                <h3>Student ID: <%=student.getId()%></h3><br>
                <h3>Username: <input type="text" name="username" value="<%=student.getUsername()%>"></h3><br>
                <h3>Password: <input type="text" name="password" value="<%=student.getPassword()%>"></h3><br>
                <h3>User type: <input type="number" name="isLibrarian" value="<%=student.isLibrarian()%>"></h3><br>
                <input type="hidden" name="id" value="<%=student.getId()%>">
                <input class="ml-3" type="submit" name="submit" value="update">
                <input class="ml-3" type="submit" name="submit" value="delete">
            </div>
        </div>
    </form>
    <%  } %>

    <h1>Add student</h1><br>
    <form action="studentServlet" method="post">
        <div class="row m-2">
            <div class="column p-2">
                <h3>Username: <input type="text" name="username"></h3><br>
                <h3>Password: <input type="text" name="password"></h3><br>
                <h3>User type: <input type="number" name="isLibrarian"></h3><br>
                <input class="ml-3" type="submit" name="submit" value="add">
            </div>
        </div>
    </form>
</div>

</body>
</html>
