package ru.isupden.extension.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlRootElement
public class City {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private Date creationDate;
    private double area;
    private long population;
    private Integer metersAboveSeaLevel;
    private Climate climate;
    private Government government;
    private StandardOfLiving standardOfLiving;
    private Human governor;
}
