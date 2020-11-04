package Classes;

import javax.validation.constraints.Null;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DBQueries {
    private static ArrayList<Book> bookList = null;
    private static ArrayList<BorrowedBook> borrowList = null;
    private static ArrayList<User> userList = null;

    public static User getUser(String username, String password) throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        PreparedStatement pstmt;
        pstmt = connection.prepareStatement("SELECT * FROM USERS WHERE username = ? AND password = ?");
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            String curUsername = resultSet.getString(2);
            String curPassword = resultSet.getString(3);
            int isLibrarian = resultSet.getInt(4);
            User user = new User(id, curUsername, curPassword, isLibrarian);
            connection.close();
            pstmt.close();
            resultSet.close();
            return user;
        }
        connection.close();
        pstmt.close();
        resultSet.close();
        return null;
    }

    public static ArrayList<User> getUserList() throws SQLException {
        if (userList != null) {
            return userList;
        }
        Connection connection = DB.getInstance().getConnection();
        PreparedStatement pstmt;
        pstmt = connection.prepareStatement("SELECT * FROM USERS");
        ResultSet resultSet = pstmt.executeQuery();
        userList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String username = resultSet.getString(2);
            String password = resultSet.getString(3);
            int isLibrarian = resultSet.getInt(4);
            User user = new User(id, username, password, isLibrarian);
            userList.add(user);
        }
        connection.close();
        pstmt.close();
        resultSet.close();
        return userList;
    }

    public static ArrayList<Book> getBookList() throws SQLException {
        if (bookList != null) {
            return bookList;
        }
        Connection connection = DB.getInstance().getConnection();
        PreparedStatement pstmt;
        pstmt = connection.prepareStatement("SELECT * FROM BOOKS");
        ResultSet resultSet = pstmt.executeQuery();
        bookList = new ArrayList<>();
        while (resultSet.next()) {
            int isbn = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String author = resultSet.getString(3);
            int count = resultSet.getInt(4);
            int availableCount = resultSet.getInt(5);
            String imageURL = resultSet.getString(6);
            Book book = new Book(isbn, title, author, count, availableCount, imageURL);
            bookList.add(book);
        }
        connection.close();
        pstmt.close();
        resultSet.close();
        return bookList;
    }

    public static ArrayList<BorrowedBook> getBorrowList() throws SQLException {
        if (borrowList != null) {
            return borrowList;
        }
        Connection connection = DB.getInstance().getConnection();
        PreparedStatement pstmt;
        pstmt = connection.prepareStatement("SELECT * FROM BORROWS");
        ResultSet resultSet = pstmt.executeQuery();
        borrowList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            int userId = resultSet.getInt(2);
            int isbn = resultSet.getInt(3);
            Date borrowDate = resultSet.getDate(4);
            Date returnDate = resultSet.getDate(5);
            BorrowedBook borrow = new BorrowedBook(id, userId, isbn, borrowDate, returnDate);
            borrowList.add(borrow);
        }
        connection.close();
        pstmt.close();
        resultSet.close();
        return borrowList;
    }

    public static User getUser(int id) throws SQLException {
        getUserList();
        for (User user : userList) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public static Book getBook(int isbn) throws SQLException {
        getBookList();
        for (Book book : bookList) {
            if (book.getIsbn() == isbn) {
                return book;
            }
        }
        return null;
    }

    public static String borrowBook(int isbn) throws SQLException {
        Book book = getBook(isbn);
        if (book.getAvailableCount() == 0 || book == null) {
            return "This book is not currently available!";
        }
        book.setAvailableCount(book.getAvailableCount()-1);
        updateBook(book);
        return "Book was borrowed successfully!";
    }

    public static String borrowBook(int isbn, int user_id) throws SQLException {
        Book book = getBook(isbn);
        if (book.getAvailableCount() == 0 || book == null) {
            return "This book is not currently available!";
        }
        book.setAvailableCount(book.getAvailableCount()-1);
        updateBook(book);
        borrowDBBook(isbn, user_id);
        return "Book was borrowed successfully!";
    }

    public static void borrowDBBook(int isbn, int user_id) throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        PreparedStatement pstmt;
        pstmt = connection.prepareStatement("INSERT INTO BORROWS(user_id, isbn) VALUES (?, ?)");
        pstmt.setInt(1, user_id);
        pstmt.setInt(2, isbn);
        pstmt.executeUpdate();
        pstmt.close();
        connection.close();
    }

    public static void returnBook(int isbn) throws SQLException {
        Book book = getBook(isbn);
        if (book == null) {
            return;
        }
        book.setAvailableCount(book.getAvailableCount()+1);
        updateBook(book);
    }

    public static String updateBook(Book curBook) throws SQLException {
        getBookList();
        for (Book book : bookList) {
            if (book.getIsbn() == curBook.getIsbn()) {
                bookList.remove(book);
                bookList.add(curBook);
                updateDBBook(curBook);
                return "Book was updated successfully!";
            }
        }
        return "Something went wrong!";
    }

    public static void updateDBBook(Book book) throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        PreparedStatement pstmt;
        pstmt = connection.prepareStatement("UPDATE BOOKS SET title=?, author=?, count=?, availableCount=?, imageURL=? WHERE isbn=?");
        pstmt.setString(1, book.getTitle());
        pstmt.setString(2, book.getAuthor());
        pstmt.setInt(3, book.getCount());
        pstmt.setInt(4, book.getAvailableCount());
        pstmt.setString(5, book.getImageURL());
        pstmt.setInt(6, book.getIsbn());
        pstmt.executeUpdate();
        pstmt.close();
        connection.close();
    }

    public static String deleteBook(int isbn) throws SQLException {
        getBookList();
        for (Book book : bookList) {
            if (book.getIsbn() == isbn) {
                if (book.getAvailableCount() == book.getCount()) {
                    bookList.remove(book);
                    deleteDBBook(isbn);
                    return "Book was deleted successfully!";
                }
            }
        }
        return "Something went wrong! Book may be in use or such book does not exist!";
    }

    public static void deleteDBBook(int isbn) throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        PreparedStatement pstmt;
        pstmt = connection.prepareStatement("DELETE FROM BOOKS WHERE isbn=?");
        pstmt.setInt(1, isbn);
        pstmt.executeUpdate();
        pstmt.close();
        connection.close();
    }

    public static String addBook(String title, String author, int count, String imageURL) throws SQLException {
        int isbn = addDBBook(title, author, count, imageURL);
        Book book = new Book(isbn, title, author, count, count, imageURL);
        getBookList().add(book);
        return "Book was added successfully!";
    }

    public static int addDBBook(String title, String author, int count, String imageURL) throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        PreparedStatement pstmt;
        pstmt = connection.prepareStatement("INSERT INTO BOOKS(title, author, count, availableCount, imageURL) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, title);
        pstmt.setString(2, author);
        pstmt.setInt(3, count);
        pstmt.setInt(4, count);
        pstmt.setString(5, imageURL);
        pstmt.executeUpdate();
        ResultSet resultSet = pstmt.getGeneratedKeys();
        int isbn = 0;
        if (resultSet.next()) {
            isbn = resultSet.getInt(1);
        }
        resultSet.close();
        pstmt.close();
        connection.close();
        return isbn;
    }



    public static String updateUser(User curUser) throws SQLException {
        getUserList();
        for (User user : userList) {
            if (user.getId() == curUser.getId()) {
                userList.remove(user);
                userList.add(curUser);
                updateDBUser(curUser);
                return "User was updated successfully!";
            }
        }
        return "Something went wrong!";
    }

    private static void updateDBUser(User user) throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        PreparedStatement pstmt;
        pstmt = connection.prepareStatement("UPDATE USERS SET username=?, password=?, isLibrarian=? WHERE id=?");
        pstmt.setString(1, user.getUsername());
        pstmt.setString(2, user.getPassword());
        pstmt.setInt(3, user.isLibrarian());
        pstmt.setInt(4, user.getId());
        pstmt.executeUpdate();
        pstmt.close();
        connection.close();
    }

    public static String deleteUser(int id) throws SQLException {
        getUserList();
        getBorrowList();
        for (User user : userList) {
            if (user.getId() == id) {
                boolean ok = true;
                for (BorrowedBook borrow : borrowList) {
                    if (borrow.getUserId() == id && borrow.getReturnDate() == null) {
                        ok = false;
                    }
                }
                if (ok) {
                    userList.remove(user);
                    deleteDBUser(id);
                    return "User was deleted successfully!";
                } else {
                    return "This user can not be deleted! User borrowed book(s).";
                }
            }
        }
        return "Such user does not exist!";
    }

    public static void deleteDBUser(int id) throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        PreparedStatement pstmt;
        pstmt = connection.prepareStatement("DELETE FROM USERS WHERE id=?");
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        pstmt.close();
        connection.close();
    }

    public static String addUser(String username, String password, int isLibrarian) throws SQLException {
        int id = addDBUser(username, password, isLibrarian);
        if (id == 0) {
            return "Something went wrong!";
        }
        User user = new User(id, username, password, isLibrarian);
        getUserList().add(user);
        return "User was added successfully!";
    }

    private static int addDBUser(String username, String password, int isLibrarian) throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        PreparedStatement pstmt;
        pstmt = connection.prepareStatement("INSERT INTO USERS(username, password, isLibrarian) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        pstmt.setInt(3, isLibrarian);
        pstmt.executeUpdate();
        ResultSet resultSet = pstmt.getGeneratedKeys();
        int id = 0;
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        resultSet.close();
        pstmt.close();
        connection.close();
        return id;
    }

    public static String deleteBorrow(int id) throws SQLException {
        getBorrowList();
        for (BorrowedBook borrow : borrowList) {
            if (borrow.getId() == id) {
                if (borrow.getReturnDate() != null) {
                    return "Book is already returned!";
                }
                java.sql.Date returnDate = (java.sql.Date) deleteDBBorrow(id);
                BorrowedBook curBorrow = borrow;
                returnBook(borrow.getIsbn());
                curBorrow.setReturnDate(returnDate);
                borrowList.remove(borrow);
                borrowList.add(curBorrow);
                return "Borrow was successfully deleted!";
            }
        }
        return "Something went wrong!";
    }

    private static Date deleteDBBorrow(int id) throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        PreparedStatement pstmt;
        pstmt = connection.prepareStatement("UPDATE BORROWS SET return_date=? WHERE id=?");
        java.sql.Date curDate = java.sql.Date.valueOf(java.time.LocalDate.now());
        pstmt.setDate(1, curDate);
        pstmt.setInt(2, id);
        pstmt.executeUpdate();
        pstmt.close();
        connection.close();
        return curDate;
    }

    public static String addBorrow(int userId, int isbn, java.sql.Date borrowDate) throws SQLException {
        int id = addDBBorrow(userId, isbn, borrowDate);
        Book book = getBook(isbn);
        User user = getUser(userId);
        java.sql.Date curDate = java.sql.Date.valueOf(java.time.LocalDate.now());
        if (id == 0 || book == null || book.getAvailableCount() == 0 || user == null || borrowDate.after(curDate)) {return "Something went wrong!";}
        borrowBook(isbn);
        BorrowedBook borrow = new BorrowedBook(id, userId, isbn, borrowDate, null);
        getBorrowList().add(borrow);
        return "Book was borrowed successfully!";
    }

    private static int addDBBorrow(int userId, int isbn, java.sql.Date borrowDate) throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        PreparedStatement pstmt;
        pstmt = connection.prepareStatement("INSERT INTO BORROWS(user_id, isbn, borrow_date) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, userId);
        pstmt.setInt(2, isbn);
        pstmt.setDate(3, borrowDate);
        pstmt.executeUpdate();
        ResultSet resultSet = pstmt.getGeneratedKeys();
        int id = 0;
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        resultSet.close();
        pstmt.close();
        connection.close();
        return id;
    }
}
