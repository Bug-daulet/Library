package Classes;

public class User {
    private int id;
    private String username;
    private String password;
    private int isLibrarian;

    public User(int id, String username, String password, int isLibrarian) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isLibrarian = isLibrarian;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int isLibrarian() {
        return isLibrarian;
    }
}
