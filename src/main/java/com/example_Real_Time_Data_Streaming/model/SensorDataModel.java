package com.example_Real_Time_Data_Streaming.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;


import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder


@Table(name="sensor_data")

public class SensorDataModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String sensorId;

    @Column(nullable = false)
    private String substanceName;

    @Column(nullable = false)
    private double value;
    @Column(nullable = false)
    private String unit;
    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;

}
