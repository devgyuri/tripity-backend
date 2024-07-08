package me.gyuri.tripity.domain.mission.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.mission.dto.CreateMissionRequest;
import me.gyuri.tripity.domain.mission.dto.UpdateMissionRequest;
import me.gyuri.tripity.domain.mission.entity.Mission;
import me.gyuri.tripity.domain.mission.repository.MissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MissionService {
    private final MissionRepository missionRepository;

    public List<Mission> findAll() {
        return missionRepository.findAll();
    }

    public Mission findById(long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public Mission save(CreateMissionRequest request) {
        return missionRepository.save(request.toEntity());
    }

    public void delete(long id) {
        missionRepository.deleteById(id);
    }

    @Transactional
    public Mission update(long id, UpdateMissionRequest request) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        mission.update(request.getTitle(), request.getName(), request.getContent());

        return mission;
    }
}
