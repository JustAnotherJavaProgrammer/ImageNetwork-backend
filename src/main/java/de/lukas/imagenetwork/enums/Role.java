package de.lukas.imagenetwork.enums;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    final String name;
    Role(String roleName) {
        this.name = roleName;
    }

    public String getName() {
        return name;
    }
}
