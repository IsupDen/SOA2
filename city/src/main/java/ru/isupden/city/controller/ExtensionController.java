package ru.isupden.city.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.isupden.city.controller.dto.CountResult;
import ru.isupden.city.model.City;
import ru.isupden.city.model.Government;
import ru.isupden.city.service.CityService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/cities", produces = MediaType.APPLICATION_XML_VALUE)
public class ExtensionController {
    private CityService cityService;

    @GetMapping("/smallest-id")
    public City getCityWithSmallestId() {
        return cityService.getFirstId();
    }

    @GetMapping("/count")
    public CountResult getCityCount(@RequestParam(value = "government") Government government) {
        return new CountResult(cityService.countByGovernment(government));
    }
}
