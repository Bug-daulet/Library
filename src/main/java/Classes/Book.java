package Classes;

public class Book {
    private int isbn;
    private String title;
    private String author;
    private int count;
    private int availableCount;
    private String imageURL;

    public Book(int isbn, String title, String author, int count, int availableCount, String imageURL) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.count = count;
        this.availableCount = availableCount;
        this.imageURL = imageURL;
    }

    public int getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getCount() {
        return count;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    public String getImageURL() {
        return imageURL;
    }
}
