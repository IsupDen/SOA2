package ru.isupden.city.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.isupden.city.controller.dto.Climates;
import ru.isupden.city.controller.dto.Governments;
import ru.isupden.city.controller.dto.StandardsOfLiving;
import ru.isupden.city.model.Climate;
import ru.isupden.city.model.Government;
import ru.isupden.city.model.StandardOfLiving;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_XML_VALUE)
public class AccessoryController {
    @GetMapping("/climate")
    public Climates getClimate() {
        return new Climates(List.of(Climate.values()));
    }

    @GetMapping("/government")
    public Governments getGovernment() {
        return new Governments(List.of(Government.values()));
    }

    @GetMapping("/standard-of-living")
    public StandardsOfLiving getStandardOfLiving() {
        return new StandardsOfLiving(List.of(StandardOfLiving.values()));
    }
}
