package ru.isupden.city.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isupden.city.util.adapter.StrictDoubleAdapter;
import ru.isupden.city.util.validator.GreaterThan;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Coordinates {
        @NotNull
        private Long x;

        @NotNull @GreaterThan(-449)
        private Double y;

        @XmlJavaTypeAdapter(StrictDoubleAdapter.class)
        public Double getY() {
                return y;
        }
}
