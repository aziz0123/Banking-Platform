package tn.esprit.spring.Entity;

import java.util.function.Consumer;

public enum Role {
    ADMIN,USER;

    public static void forEach(Consumer<Role> action) {
        for (Role role : values()) {
            action.accept(role);
        }
    }
}
