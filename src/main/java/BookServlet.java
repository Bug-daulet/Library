import Classes.Book;
import Classes.DBQueries;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "BookServlet", value = "/bookServlet")
public class BookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String submit = request.getParameter("submit");
        switch (submit) {
            case "update":
            {
                int isbn = Integer.parseInt(request.getParameter("bookIsbn"));
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                int count = Integer.parseInt(request.getParameter("count"));
                int availableCount = Integer.parseInt(request.getParameter("availableCount"));
                String imageURL = request.getParameter("imageURL");
                Book book = new Book(isbn, title, author, count, availableCount, imageURL);

                try {
                    String result = DBQueries.updateBook(book);
                    request.setAttribute("result", result);
                    request.getRequestDispatcher("bookControl.jsp").forward(request, response);
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }

                break;
            }
            case "delete":
            {
                int isbn = Integer.parseInt(request.getParameter("bookIsbn"));

                try {
                    String result = DBQueries.deleteBook(isbn);
                    request.setAttribute("result", result);
                    request.getRequestDispatcher("bookControl.jsp").forward(request, response);
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }

                break;
            }
            case "add":
            {
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                int count = Integer.parseInt(request.getParameter("count"));
                String imageURL = request.getParameter("imageURL");

                try {
                    String result = DBQueries.addBook(title, author, count, imageURL);
                    request.setAttribute("result", result);
                    request.getRequestDispatcher("bookControl.jsp").forward(request, response);
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
