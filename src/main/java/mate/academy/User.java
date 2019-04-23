package mate.academy;

public class User {
    private String name;
    private String password;

    protected User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    protected String getName() {
        return name;
    }

    protected String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
