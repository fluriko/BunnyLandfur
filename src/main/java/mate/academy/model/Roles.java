package mate.academy.model;

public enum Roles {
    ADMIN(1, "ADMIN"),
    USER(2, "USER");

    private int id;
    private String name;

    Roles(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
