package me.gyuri.tripity.domain.mission.dto;

import lombok.Getter;
import me.gyuri.tripity.domain.mission.entity.Mission;

@Getter
public class MissionResponse {
    private final String title;
    private final String name;
    private final String content;

    public MissionResponse(Mission mission) {
        this.title = mission.getTitle();
        this.name = mission.getName();
        this.content = mission.getContent();
    }
}
