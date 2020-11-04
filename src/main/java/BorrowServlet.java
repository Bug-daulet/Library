import Classes.DBQueries;
import Classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "BorrowServlet", value = "/borrowServlet")
public class BorrowServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String submit = request.getParameter("submit");
        switch (submit) {
            case "delete":
            {
                int id = Integer.parseInt(request.getParameter("id"));

                try {
                    String result = DBQueries.deleteBorrow(id);
                    request.setAttribute("result", result);
                    request.getRequestDispatcher("borrowControl.jsp").forward(request, response);
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }

                break;
            }
            case "add":
            {
                int userId = Integer.parseInt(request.getParameter("userId"));
                int isbn = Integer.parseInt(request.getParameter("isbn"));
                Date borrowDate = Date.valueOf(request.getParameter("borrowDate"));

                try {
                    String result = DBQueries.addBorrow(userId, isbn, borrowDate);
                    request.setAttribute("result", result);
                    request.getRequestDispatcher("borrowControl.jsp").forward(request, response);
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }

                break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
