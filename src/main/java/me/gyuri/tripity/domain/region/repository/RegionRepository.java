package me.gyuri.tripity.domain.region.repository;

import me.gyuri.tripity.domain.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
