package me.gyuri.tripity.domain.place.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gyuri.tripity.domain.city.entity.City;
import me.gyuri.tripity.domain.region.entity.Region;

@Table(name = "place")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "postcode")
    private Integer postcode;

    @Column(name = "mapX")
    private Double mapX;

    @Column(name = "mapY")
    private Double mapY;

    @Column(name = "image")
    private String image;

    @ManyToOne
    private Region region;

    @ManyToOne
    private City city;

    @Builder
    public Place(String name, String description, String address, int postcode, double mapX, double mapY, String image, Region region, City city) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.postcode = postcode;
        this.mapX = mapX;
        this.mapY = mapY;
        this.image = image;
        this.region = region;
        this.city = city;
    }
}
