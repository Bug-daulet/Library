import Classes.DBQueries;
import Classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Borrow", value = "/borrow")
public class Borrow extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int isbn = Integer.parseInt(request.getParameter("bookIsbn"));

        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
        } else {
            try {
                User user = (User) request.getSession().getAttribute("user");
                DBQueries.borrowBook(isbn, user.getId());
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
