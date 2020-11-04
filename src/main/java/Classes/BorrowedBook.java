package Classes;

import java.util.Date;

public class BorrowedBook {
    private int id;
    private int userId;
    private int isbn;
    private Date borrowDate;
    private Date returnDate;

    public BorrowedBook(int id, int userId, int isbn, Date borrowDate, Date returnDate) {
        this.id = id;
        this.userId = userId;
        this.isbn = isbn;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getIsbn() {
        return isbn;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }
}
