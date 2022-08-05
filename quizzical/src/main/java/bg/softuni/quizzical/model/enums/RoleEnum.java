package bg.softuni.quizzical.model.enums;

import bg.softuni.quizzical.model.entity.Role;

import java.util.HashSet;
import java.util.Set;

public enum RoleEnum {
    ROLE_ADMIN("ROLE_ADMIN"), ROLE_TEACHER("ROLE_TEACHER"), ROLE_STUDENT("ROLE_STUDENT");

    RoleEnum(String role) {
        this.roles = new HashSet<>();
        this.roles.add(new Role(role));
    }

    Set<Role> roles;
}
