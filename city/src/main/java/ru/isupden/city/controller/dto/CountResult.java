package ru.isupden.city.controller.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "result")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CountResult {
    private long result;

    @XmlValue
    public long getResult() {
        return result;
    }
}
