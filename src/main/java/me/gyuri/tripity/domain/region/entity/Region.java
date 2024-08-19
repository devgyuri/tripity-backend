package me.gyuri.tripity.domain.region.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gyuri.tripity.domain.city.entity.City;
import me.gyuri.tripity.domain.place.entity.Place;

import java.util.List;

@Table(name = "region")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private Integer code;

    @OneToMany(mappedBy = "region", cascade = CascadeType.PERSIST)
    private List<City> cityList;

    @OneToMany(mappedBy = "region", cascade = CascadeType.PERSIST)
    private List<Place> placeList;

    @Builder
    public Region(String name, int code) {
        this.name = name;
        this.code = code;
    }
}
