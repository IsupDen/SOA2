package ru.isupden.city.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.isupden.city.model.City;
import ru.isupden.city.model.Government;

@Repository
public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {
    City findFirstByOrderByIdAsc();
    long countByGovernmentGreaterThan(Government government);
}
