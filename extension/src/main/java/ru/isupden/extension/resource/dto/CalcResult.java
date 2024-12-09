package ru.isupden.extension.resource.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "result")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalcResult {
    private double result;

    @XmlValue
    public double getResult() {
        return result;
    }
}
