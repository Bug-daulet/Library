<%@ page import="Classes.User" %>
<%@ page import="Classes.Book" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Classes.DBQueries" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: HP Omen 15
  Date: 04.11.2020
  Time: 9:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) session.getAttribute("user");
    ArrayList<Book> bookList = new ArrayList<>();
    try {
        bookList = DBQueries.getBookList();
    } catch (SQLException throwable) {
        throwable.printStackTrace();
    }
%>
<html>
<head>
    <title>Library main</title>
    <link rel="stylesheet" href="main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
</head>
<body>
<div id="navbar">
    <ul>
        <li><a href="index.jsp">Main</a></li>
        <%  if (user != null && user.isLibrarian() == 1) {%>
        <li><a href="bookControl.jsp">Books control</a></li>
        <li><a href="studentControl.jsp">Students control</a></li>
        <li><a href="borrowControl.jsp">Borrow control</a></li>
        <%  }%>
        <li><a href="profile.jsp">Profile</a></li>
        <li><a href="login.jsp">Log in</a></li>
        <li><a href="logout.jsp">Log out</a></li>
    </ul>
</div>


<div class="container">
    <%
        for (Book book : bookList) { %>
    <form id="book-<%=book.getIsbn()%>" action="borrow" method="post">
        <div class="row m-2">
            <div class="column m-2">
                <img width="250px" height="350px" src="<%=book.getImageURL()%>">
            </div>
            <div class="column m-2">
                <h3><%=book.getTitle()%></h3><br>
                <p><%=book.getAuthor()%></p><br>
                <p>Count of available books: <%=book.getAvailableCount()%> </p>
                <input type="hidden" name="bookIsbn" value="<%=book.getIsbn()%>">
                <input type="submit" name="submit-<%=book.getIsbn()%>" value="Get this book">
            </div>
        </div>
    </form>
    <%  } %>
</div>

</body>
</html>
