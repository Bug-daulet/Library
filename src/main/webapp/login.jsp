<%@ page import="Classes.User" %><%--
  Created by IntelliJ IDEA.
  User: HP Omen 15
  Date: 04.11.2020
  Time: 9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) session.getAttribute("user");
    if (user != null) {
        response.sendRedirect("index.jsp");
    }
%>
<html>
<head>
    <title>Library login</title>
    <link rel="stylesheet" href="main.css">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:700,600' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
    <style>
        body{
            font-family: 'Open Sans', sans-serif;
            margin: 0 auto 0 auto;
            width:100%;
            text-align:center;
        }

        p{
            font-size:12px;
            text-decoration: none;
            color:#ffffff;
        }

        h1{
            font-size:1.5em;
            color:#525252;
        }

        .box{
            background:white;
            width:300px;
            border-radius:6px;
            margin: 100px auto 0 auto;
            padding:0px 0px 70px 0px;
            border: #2980b9 4px solid;
        }

        .email{
            background:#ecf0f1;
            border: #ccc 1px solid;
            border-bottom: #ccc 2px solid;
            padding: 8px;
            width:250px;
            color:#AAAAAA;
            margin-top:10px;
            font-size:1em;
            border-radius:4px;
        }

        .password{
            border-radius:4px;
            background:#ecf0f1;
            border: #ccc 1px solid;
            padding: 8px;
            width:250px;
            font-size:1em;
        }

        .btn{
            background:#2ecc71;
            width:125px;
            padding-top:5px;
            padding-bottom:5px;
            color:white;
            border-radius:4px;
            border: #27ae60 1px solid;

            margin-top:20px;
            margin-bottom:20px;
            float:left;
            margin-left:16px;
            font-weight:800;
            font-size:0.8em;
        }

        .btn:hover{
            background:#2CC06B;
        }
    </style>
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


<div class="container d-flex justify-content-center">
    <%  String result = (String) request.getAttribute("result");
        if (result != null) { %>
    <div id="displayResult" class="row alert alert-primary m-2" role="alert">
        <div class="column"><h3><%=result%></h3></div>
    </div>
    <script type="text/javascript">
        $("#displayResult").show().delay(5000).fadeOut();
    </script>
    <%  } %>

    <form method="post" action="Login">
        <div class="box">
            <h1>Library login</h1>

            <input type="text" name="username" onFocus="field_focus(this, 'username');" onblur="field_blur(this, 'username');" class="email" />

            <input type="password" name="password" onFocus="field_focus(this, 'password');" onblur="field_blur(this, 'password');" class="email" />

            <input class="btn" type="submit" value="submit">

        </div>

    </form>

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        function field_focus(field, email)
        {
            if(field.value == email)
            {
                field.value = '';
            }
        }

        function field_blur(field, email)
        {
            if(field.value == '')
            {
                field.value = email;
            }
        }

        //Fade in dashboard box
        $(document).ready(function(){
            $('.box').hide().fadeIn(2000);
        });

        //Stop click event
        $('a').click(function(event){
            event.preventDefault();
        });
    </script>
</div>
</body>
</html>
