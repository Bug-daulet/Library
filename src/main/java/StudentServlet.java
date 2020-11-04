import Classes.Book;
import Classes.DBQueries;
import Classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "StudentServlet", value = "/studentServlet")
public class StudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String submit = request.getParameter("submit");
        switch (submit) {
            case "update":
            {
                int id = Integer.parseInt(request.getParameter("id"));
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                int isLibrarian = Integer.parseInt(request.getParameter("isLibrarian"));
                User user = new User(id, username, password, isLibrarian);

                try {
                    String result = DBQueries.updateUser(user);
                    request.setAttribute("result", result);
                    request.getRequestDispatcher("studentControl.jsp").forward(request, response);
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }

                break;
            }
            case "delete":
            {
                int id = Integer.parseInt(request.getParameter("id"));

                try {
                    String result = DBQueries.deleteUser(id);
                    request.setAttribute("result", result);
                    request.getRequestDispatcher("studentControl.jsp").forward(request, response);
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }

                break;
            }
            case "add":
            {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                int isLibrarian = Integer.parseInt(request.getParameter("isLibrarian"));

                try {
                    String result = DBQueries.addUser(username, password, isLibrarian);
                    request.setAttribute("result", result);
                    request.getRequestDispatcher("studentControl.jsp").forward(request, response);
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
