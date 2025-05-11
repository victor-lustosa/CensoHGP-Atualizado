package br.com.unitins.censohgp.models.enums;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Getter
public enum Profile {

    ADMIN(1, "ROLE_ADMIN"),
    NURSE(2, "ROLE_NURSE");

    private final int code;
    private final String description;

    Profile(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static List<String> getAllProfileNames() {
        return Stream.of(Profile.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public static int findIdByName(String name) {
        return Stream.of(Profile.values())
                .filter(p -> p.name().equalsIgnoreCase(name))
                .map(Profile::getCode)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid name: " + name));
    }

    public static Profile toEnum(Integer code) {
        if (code == null) return null;

        return Stream.of(Profile.values())
                .filter(p -> p.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + code));
    }
}
