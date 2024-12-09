package ru.isupden.city.controller.dto;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@XmlRootElement(name = "error")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true, setterPrefix = "set", builderMethodName = "newBuilder")
@With
@Data
public class Error {
    private String message;
    private List<String> errors;
    private List<FieldValidationError> fields;

    @XmlElement(nillable = true)
    public List<String> getErrors() {
        return errors;
    }

    @XmlElementWrapper(name = "fields")
    @XmlElement(name = "field")
    public List<FieldValidationError> getFields() {
        return fields;
    }
}
