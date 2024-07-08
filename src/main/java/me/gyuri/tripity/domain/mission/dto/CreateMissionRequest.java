package me.gyuri.tripity.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gyuri.tripity.domain.mission.entity.Mission;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateMissionRequest {
    private String title;
    private String name;
    private String content;

    public Mission toEntity() {
        return Mission.builder()
                .title(title)
                .name(name)
                .content(content)
                .build();
    }
}
