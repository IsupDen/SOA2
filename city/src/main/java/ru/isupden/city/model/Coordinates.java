package ru.isupden.city.model;

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
public class Coordinates {
    private Long x;
    private Double y;
}
