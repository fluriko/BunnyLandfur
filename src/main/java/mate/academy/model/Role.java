package mate.academy.model;

import java.util.Objects;

public class Role {
    private int id;
    private Roles name;

    public Role(int id) {
        if (id <= Roles.values().length) {
            this.id = id;
        } else {
            this.id = 2;
        }
        name = Roles.values()[id - 1];
    }

    public Role() {
        this(2);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Roles getName() {
        return name;
    }

    public void setName(Roles name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return id == role.id &&
                name == role.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
