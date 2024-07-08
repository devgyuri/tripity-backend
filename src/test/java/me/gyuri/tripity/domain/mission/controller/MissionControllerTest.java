package me.gyuri.tripity.domain.mission.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.gyuri.tripity.domain.mission.dto.CreateMissionRequest;
import me.gyuri.tripity.domain.mission.dto.UpdateMissionRequest;
import me.gyuri.tripity.domain.mission.entity.Mission;
import me.gyuri.tripity.domain.mission.repository.MissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class MissionControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    MissionRepository missionRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        missionRepository.deleteAll();
    }

    @DisplayName("fetchMissions: 미션 목록 조회에 성공한다.")
    @Test
    public void fetchMissions() throws Exception {
        // given
        final String url = "/missions";
        final String title = "mission1";
        final String name = "n1";
        final String content = "c1";

        missionRepository.save(Mission.builder()
                .title(title)
                .name(name)
                .content(content)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].content").value(content));
    }

    @DisplayName("fetchMission: 미션 조회에 성공한다.")
    @Test
    public void fetchMission() throws Exception {
        // given
        final String url = "/missions/{id}";
        final String title = "mission2";
        final String name = "n2";
        final String content = "c2";

        Mission savedMission = missionRepository.save(Mission.builder()
                .title(title)
                .name(name)
                .content(content)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, savedMission.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.content").value(content));
    }

    @DisplayName("createMission: 미션 추가에 성공한다.")
    @Test
    public void createMission() throws Exception {
        // given
        final String url = "/missions";
        final String title = "mission3";
        final String name = "n3";
        final String content = "c3";
        final CreateMissionRequest userRequest = new CreateMissionRequest(title, name, content);

        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Mission> missions = missionRepository.findAll();

        assertThat(missions.size()).isEqualTo(1);
        assertThat(missions.get(0).getTitle()).isEqualTo(title);
        assertThat(missions.get(0).getName()).isEqualTo(name);
        assertThat(missions.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("deleteMission: 미션 삭제에 성공한다.")
    @Test
    public void deleteMission() throws Exception {
        // given
        final String url = "/missions/{id}";
        final String title = "mission4";
        final String name = "n4";
        final String content = "c4";

        Mission savedMission = missionRepository.save(Mission.builder()
                .title(title)
                .name(name)
                .content(content)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(delete(url, savedMission.getId()))
                .andExpect(status().isOk());

        // then
        List<Mission> missions = missionRepository.findAll();

        assertThat(missions).isEmpty();
    }

    @DisplayName("updateMission: 미션 수정에 성공한다.")
    @Test
    public void updateMission() throws Exception {
        // given
        final String url = "/missions/{id}";
        final String title = "mission5";
        final String name = "n5";
        final String content = "c5";

        Mission savedMission = missionRepository.save(Mission.builder()
                .title(title)
                .name(name)
                .content(content)
                .build());

        final String newTitle = "new mission";
        final String newName = "new name";
        final String newContent = "new content";

        UpdateMissionRequest request = new UpdateMissionRequest(newTitle, newName, newContent);

        // when
        final ResultActions resultActions = mockMvc.perform(put(url, savedMission.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions.andExpect(status().isOk());

        Mission mission = missionRepository.findById(savedMission.getId()).get();

        assertThat(mission.getTitle()).isEqualTo(newTitle);
        assertThat(mission.getName()).isEqualTo(newName);
        assertThat(mission.getContent()).isEqualTo(newContent);
    }
}