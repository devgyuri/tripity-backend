package me.gyuri.tripity.domain.mission.controller;

import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.mission.dto.CreateMissionRequest;
import me.gyuri.tripity.domain.mission.dto.MissionResponse;
import me.gyuri.tripity.domain.mission.dto.UpdateMissionRequest;
import me.gyuri.tripity.domain.mission.entity.Mission;
import me.gyuri.tripity.domain.mission.service.MissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MissionController {
    private final MissionService missionService;

    @GetMapping("/api/missions")
    public ResponseEntity<List<MissionResponse>> fetchMissions() {
        List<MissionResponse> missions = missionService.findAll()
                .stream()
                .map(MissionResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(missions);
    }

    @GetMapping("/api/missions/{id}")
    public ResponseEntity<MissionResponse> fetchMission(@PathVariable(value = "id") long id) {
        Mission mission = missionService.findById(id);

        return ResponseEntity.ok()
                .body(new MissionResponse(mission));
    }

    @PostMapping("/api/missions")
    public ResponseEntity<Mission> createMission(@RequestBody CreateMissionRequest request) {
        Mission savedMission = missionService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedMission);
    }


    @DeleteMapping("/api/missions/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable(value = "id") long id) {
        missionService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/missions/{id}")
    public ResponseEntity<Mission> updateMission(@PathVariable(value = "id") long id, @RequestBody UpdateMissionRequest request) {
        Mission updatedMission = missionService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedMission);
    }
}
