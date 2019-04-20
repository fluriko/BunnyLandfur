package mate.academy;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String password;
    private static List<User> storage = new ArrayList<>();

    protected User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    protected static void addUser(User user) {
        storage.add(user);
    }

    protected static User getUser(User user) {
        for (int i = 0; i < storage.size(); i++) {
            if (user.equals(storage.get(i))) {
                return storage.get(i);
            }
        }
        throw new NoSuchUserException();
    }

    protected static List<User> getUsers() {
        return storage;
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
