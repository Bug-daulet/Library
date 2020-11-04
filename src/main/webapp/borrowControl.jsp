<%@ page import="Classes.User" %>
<%@ page import="Classes.Book" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Classes.DBQueries" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="Classes.BorrowedBook" %><%--
  Created by IntelliJ IDEA.
  User: HP Omen 15
  Date: 04.11.2020
  Time: 11:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || user.isLibrarian() == 0) {
        response.sendRedirect("index.jsp");
    }

    ArrayList<BorrowedBook> borrowList = new ArrayList<>();
    try {
        borrowList = DBQueries.getBorrowList();
    } catch (SQLException throwable) {
        throwable.printStackTrace();
    }
%>
<html>
<head>
    <title>Library borrow control</title>
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

    <%  for (BorrowedBook borrow : borrowList) { %>
            <form id="borrow-<%=borrow.getId()%>" action="borrowServlet" method="post">
                <div class="row m-2 border border-primary">
                    <div class="column p-2">
                        <h3>Borrow ID: <%=borrow.getId()%></h3><br>
                        <h3>User ID: <%=borrow.getUserId()%></h3><br>
                        <h3>Book ISBN: <%=borrow.getIsbn()%></h3><br>
                        <h3>Borrow date: <%=borrow.getBorrowDate()%></h3><br>
                        <h3>Return date:
                        <%  if (borrow.getReturnDate() != null) { %>
                                <%=borrow.getReturnDate()%>
                        <%  } else { %>
                                Not returned yet!
                        <%  } %>
                        </h3><br>
                        <input type="hidden" name="id" value="<%=borrow.getId()%>">
                        <input class="ml-3" type="submit" name="submit" value="delete">
                    </div>
                </div>
            </form>
    <%  } %>

    <h1>Add borrow</h1><br>
    <form action="borrowServlet" method="post">
        <div class="row m-2">
            <div class="column p-2">
                <h3>User ID: <input type="number" name="userId"></h3><br>
                <h3>ISBN: <input type="number" name="isbn"></h3><br>
                <h3>Borrow date: <input type="date" name="borrowDate"></h3><br>
                <input class="ml-3" type="submit" name="submit" value="add">
            </div>
        </div>
    </form>
</div>

</body>
</html>
