package ru.isupden.city.controller.dto;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.isupden.city.model.Climate;

@XmlRootElement(name = "climates")
@NoArgsConstructor
@AllArgsConstructor
public class Climates {
    private List<Climate> climates;

    @XmlElement(name = "climate")
    public List<Climate> getClimates() {
        return climates;
    }
}
