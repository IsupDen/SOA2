package ru.isupden.city.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.isupden.city.controller.dto.CityRequest;
import ru.isupden.city.exception.EntityNotFoundException;
import ru.isupden.city.model.City;
import ru.isupden.city.model.Coordinates;
import ru.isupden.city.model.Government;
import ru.isupden.city.model.Human;
import ru.isupden.city.repo.CityRepository;

@RequiredArgsConstructor
@Service
public class CityService {
    private final CityRepository cityRepository;
    private final Pattern filterPattern = Pattern.compile("(.*)\\((.*)\\)=(.*)");

    public City getCityById(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "city"));
    }

    public List<City> getAllCities(Integer limit, Integer offset, List<String> sort, List<String> filters) {
        Pageable pageable = PageRequest.of(offset != null ? offset : 0, limit != null ? limit : 10, createSort(sort));
        Specification<City> specification = createSpecification(filters);
        return cityRepository.findAll(specification, pageable).getContent();
    }

    public City createCity(CityRequest request) {
        var city = City.newBuilder()
                .setName(request.getName())
                .setCoordinates(Coordinates.newBuilder()
                        .setX(request.getCoordinates().getX())
                        .setY(request.getCoordinates().getY())
                        .build())
                .setCreationDate(new Date())
                .setArea(request.getArea())
                .setPopulation(request.getPopulation())
                .setMetersAboveSeaLevel(request.getMetersAboveSeaLevel())
                .setClimate(request.getClimate())
                .setGovernment(request.getGovernment())
                .setStandardOfLiving(request.getStandardOfLiving())
                .setGovernor(Human.newBuilder()
                        .setName(request.getGovernor().getName())
                        .build());
        return cityRepository.save(city.build());
    }

    public City updateCity(CityRequest request, Long id) {
        var city = cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "city"));
        return cityRepository.save(city.toBuilder()
                .setName(request.getName())
                .setCoordinates(Coordinates.newBuilder()
                        .setX(request.getCoordinates().getX())
                        .setY(request.getCoordinates().getY())
                        .build())
                .setArea(request.getArea())
                .setPopulation(request.getPopulation())
                .setMetersAboveSeaLevel(request.getMetersAboveSeaLevel())
                .setClimate(request.getClimate())
                .setGovernment(request.getGovernment())
                .setStandardOfLiving(request.getStandardOfLiving())
                .setGovernor(Human.newBuilder()
                        .setName(request.getGovernor().getName())
                        .build())
                .build());
    }

    public void deleteCity(Long id) {
        cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "city"));
        cityRepository.deleteById(id);
    }

    public long count() {
        return cityRepository.count();
    }

    public City getFirstId() {
        var city = cityRepository.findFirstByOrderByIdAsc();
        if (city == null) {
            throw new EntityNotFoundException(0L, "city");
        }
        return city;
    }

    public long countByGovernment(Government government) {
        return cityRepository.countByGovernmentGreaterThan(government);
    }

    private Sort createSort(List<String> sortParams) {
        if (sortParams == null || sortParams.isEmpty()) {
            return Sort.unsorted();
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (String param : sortParams) {
            if (param.startsWith("-")) {
                orders.add(Sort.Order.desc(param.substring(1)));
            } else {
                orders.add(Sort.Order.asc(param));
            }
        }

        return Sort.by(orders);
    }

    private Specification<City> createSpecification(List<String> filters) {
        if (filters == null || filters.isEmpty()) {
            return null;
        }

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (String filter : filters) {
                Matcher matcher = filterPattern.matcher(filter);
                String fieldName, value, operation;

                if (matcher.find()){
                    fieldName = matcher.group(1);
                    operation = matcher.group(2);
                    value = matcher.group(3);
                } else {
                    throw new IllegalArgumentException("Invalid filter: " + filter);
                }


                switch (operation) {
                    case "eq":
                        predicates.add(cb.equal(root.get(fieldName), value));
                        break;
                    case "neq":
                        predicates.add(cb.notEqual(root.get(fieldName), value));
                        break;
                    case "lt":
                        predicates.add(cb.lessThan(root.get(fieldName), value));
                        break;
                    case "lte":
                        predicates.add(cb.lessThanOrEqualTo(root.get(fieldName), value));
                        break;
                    case "gt":
                        predicates.add(cb.greaterThan(root.get(fieldName), value));
                        break;
                    case "gte":
                        predicates.add(cb.greaterThanOrEqualTo(root.get(fieldName), value));
                        break;
                    case "~":
                        predicates.add(cb.like(root.get(fieldName), value + "%"));
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operation: " + operation);
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
