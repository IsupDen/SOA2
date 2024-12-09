package ru.isupden.city.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true, setterPrefix = "set", builderMethodName = "newBuilder")
@With
@Data
@XmlRootElement(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "text")
    private String name;
    @Embedded
    private Coordinates coordinates;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date creationDate;
    private double area;
    private long population;
    private Integer metersAboveSeaLevel;
    @Enumerated(value = EnumType.STRING)
    private Climate climate;
    @Enumerated(value = EnumType.STRING)
    private Government government;
    @Enumerated(value = EnumType.STRING)
    private StandardOfLiving standardOfLiving;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "governor_name"))
    })
    private Human governor;
}
