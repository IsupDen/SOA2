package ru.isupden.city.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EntityNotFoundException extends RuntimeException {
    private Long id;
    private String name;

    @Override
    public String getMessage() {
        return "%s not found; id: %s".formatted(name, id);
    }
}
