package ru.isupden.city.controller.dto;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.isupden.city.model.StandardOfLiving;

@XmlRootElement(name = "standardsOfLiving")
@NoArgsConstructor
@AllArgsConstructor
public class StandardsOfLiving {
    private List<StandardOfLiving> standardsOfLiving;

    @XmlElement(name = "standardOfLiving")
    public List<StandardOfLiving> getStandardsOfLiving() {
        return standardsOfLiving;
    }
}
