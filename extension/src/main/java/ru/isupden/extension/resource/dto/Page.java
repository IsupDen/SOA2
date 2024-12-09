package ru.isupden.extension.resource.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isupden.extension.model.City;

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
