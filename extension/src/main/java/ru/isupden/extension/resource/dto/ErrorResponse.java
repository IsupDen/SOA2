package ru.isupden.extension.resource.dto;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlRootElement(name = "error")
public class ErrorResponse {
    private String type;
    private String message;
}
