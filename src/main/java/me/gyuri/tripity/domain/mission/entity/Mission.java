package me.gyuri.tripity.domain.mission.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content")
    private String content;

    @Builder
    public Mission(String title, String name, String content) {
        this.title = title;
        this.name = name;
        this.content = content;
    }

    public void update(String title, String name, String content) {
        this.title = title;
        this.name = name;
        this.content = content;
    }
}
