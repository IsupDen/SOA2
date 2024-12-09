package ru.isupden.city.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.isupden.city.controller.dto.CityRequest;
import ru.isupden.city.controller.dto.Page;
import ru.isupden.city.model.City;
import ru.isupden.city.service.CityService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/cities", produces = MediaType.APPLICATION_XML_VALUE)
@Validated
public class CityController {
    private final CityService cityService;

    @GetMapping("/{id}")
    public City get(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    @GetMapping
    public Page getAll(
            @RequestParam(value = "limit", required = false, defaultValue = "10") @Positive Integer limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(value = "sort", required = false) List<String> sort,
            @RequestParam(value = "filter", required = false) List<String> filters
    ) {
        var cities = cityService.getAllCities(limit, offset, sort, filters);
        var totalItems = cityService.count();
        var totalPages = (long) Math.ceil((double) totalItems / limit);
        var currentPage = offset + 1;

        return new Page(totalItems, cities, totalPages, currentPage);
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public City create(@Valid @RequestBody CityRequest city) {
        return cityService.createCity(city);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_XML_VALUE)
    public City update(@PathVariable Long id, @RequestBody CityRequest city) {
        return cityService.updateCity(city, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cityService.deleteCity(id);
    }
}
