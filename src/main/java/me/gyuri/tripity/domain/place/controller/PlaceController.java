package me.gyuri.tripity.domain.place.controller;

import lombok.RequiredArgsConstructor;
import me.gyuri.tripity.domain.mission.dto.MissionResponse;
import me.gyuri.tripity.domain.place.service.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/api/places/test")
    public void openApiTest() {
        try {
            placeService.initializeDB();
        } catch (Exception e) {
            return;
        }
    }
}
