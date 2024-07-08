package me.gyuri.tripity.domain.mission.repository;

import me.gyuri.tripity.domain.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}
