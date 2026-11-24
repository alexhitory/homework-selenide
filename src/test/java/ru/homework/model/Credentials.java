package ru.homework.model;

import java.util.Objects;

/** Value Object: empty fields are allowed for negative login scenarios. */
public record Credentials(String username, String password) {
    public Credentials {
        Objects.requireNonNull(username, "username");
        Objects.requireNonNull(password, "password");
    }

    @Override
    public String toString() {
        return "Credentials[username=" + username + ", password=***]";
    }
}
