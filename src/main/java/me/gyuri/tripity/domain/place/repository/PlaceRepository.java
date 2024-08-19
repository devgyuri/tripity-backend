package me.gyuri.tripity.domain.place.repository;

import me.gyuri.tripity.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
