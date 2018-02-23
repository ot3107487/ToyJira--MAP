package sample.domain;

public class User {
    private int id;
    private String nume;
    private UserType type;

    public User(int id, String nume, UserType type) {
        this.id = id;
        this.nume = nume;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
