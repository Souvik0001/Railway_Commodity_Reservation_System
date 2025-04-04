package com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double rating;

    private Boolean available;

    private String trainId;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point currentLocation;
}
