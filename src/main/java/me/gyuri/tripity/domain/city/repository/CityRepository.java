package me.gyuri.tripity.domain.city.repository;

import me.gyuri.tripity.domain.city.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
