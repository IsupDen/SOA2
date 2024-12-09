package ru.isupden.city.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true, setterPrefix = "set", builderMethodName = "newBuilder")
@With
@Data
@Embeddable
public class Human {
    @Column(columnDefinition = "text")
    private String name;
}
