package ru.isupden.city.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isupden.city.model.Climate;
import ru.isupden.city.model.Government;
import ru.isupden.city.model.StandardOfLiving;

@XmlRootElement(name = "city")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CityRequest {
        @NotNull @NotEmpty
        private String name;

        @NotNull @Valid
        private Coordinates coordinates;

        @Positive
        private double area;

        @Positive
        private Integer population;

        private Integer metersAboveSeaLevel;

        @NotNull
        private Climate climate;

        @NotNull
        private Government government;

        @NotNull
        private StandardOfLiving standardOfLiving;

        @NotNull @Valid
        private Human governor;
}
