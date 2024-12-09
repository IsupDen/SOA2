package ru.isupden.city.controller.dto;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.isupden.city.model.Government;

@XmlRootElement(name = "governments")
@NoArgsConstructor
@AllArgsConstructor
public class Governments {
    private List<Government> governments;

    @XmlElement(name = "government")
    public List<Government> getGovernments() {
        return governments;
    }
}
