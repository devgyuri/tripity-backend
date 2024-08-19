package me.gyuri.tripity.domain.city.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gyuri.tripity.domain.place.entity.Place;
import me.gyuri.tripity.domain.region.entity.Region;

import java.util.List;

@Table(name = "city")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private Integer code;

    @ManyToOne
    private Region region;

    @OneToMany(mappedBy = "city", cascade = CascadeType.PERSIST)
    private List<Place> placeList;

    @Builder
    public City(String name, int code, Region region) {
        this.name = name;
        this.code = code;
        this.region = region;
    }
}
