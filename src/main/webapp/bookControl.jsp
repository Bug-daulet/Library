<%@ page import="Classes.User" %>
<%@ page import="Classes.Book" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Classes.DBQueries" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="Classes.BorrowedBook" %><%--
  Created by IntelliJ IDEA.
  User: HP Omen 15
  Date: 04.11.2020
  Time: 9:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || user.isLibrarian() == 0) {
        response.sendRedirect("index.jsp");
    }

    ArrayList<Book> bookList = new ArrayList<>();
    try {
        bookList = DBQueries.getBookList();
    } catch (SQLException throwable) {
        throwable.printStackTrace();
    }
%>
<html>
<head>
    <title>Library book control</title>
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
        for (Book book : bookList) { %>
    <form id="book-<%=book.getIsbn()%>" action="bookServlet" method="post">
        <div class="row m-2">
            <div class="column p-2">
                <img width="350px" height="450px" src="<%=book.getImageURL()%>">
            </div>
            <div class="column p-2">
                <h3><input type="text" name="title" value="<%=book.getTitle()%>"></h3><br>
                <p><input type="text" name="author" value="<%=book.getAuthor()%>"></p><br>
                <p>ISBN: <%=book.getIsbn()%> </p><br>
                <p>Count of books: <input type="number" name="count" value="<%=book.getCount()%>"> </p><br>
                <p>Count of available books: <input type="number" name="availableCount" value="<%=book.getAvailableCount()%>"> </p><br>
                <p>Image url: <input type="url" name="imageURL" value="<%=book.getImageURL()%>"></p><br>
                <input type="hidden" name="bookIsbn" value="<%=book.getIsbn()%>">
                <input class="ml-3" type="submit" name="submit" value="update">
                <input class="ml-3" type="submit" name="submit" value="delete">
            </div>
        </div>
    </form>
    <%  } %>

    <h1>Add book</h1><br>
    <form action="bookServlet" method="post">
        <div class="row m-2">
            <div class="column p-2">
                <h3>Title: <input type="text" name="title"></h3><br>
                <p>Author: <input type="text" name="author"></p><br>
                <p>Count of books: <input type="number" name="count"></p><br>
                <p>Image url: <input type="url" name="imageURL"></p><br>
                <input class="ml-3" type="submit" name="submit" value="add">
            </div>
        </div>
    </form>
</div>

</body>
</html>
