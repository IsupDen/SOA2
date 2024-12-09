package ru.isupden.city.controller.dto;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isupden.city.model.City;

@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Page {
    private long totalItems;
    private List<City> cities;
    private long totalPages;
    private long currentPage;

    @XmlElementWrapper(name = "cities")
    @XmlElement(name = "city")
    public List<City> getCities() {
        return cities;
    }
}
