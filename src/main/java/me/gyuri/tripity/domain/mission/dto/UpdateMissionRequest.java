package me.gyuri.tripity.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateMissionRequest {
    private String title;
    private String name;
    private String content;
}
