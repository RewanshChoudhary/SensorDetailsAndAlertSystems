package com.example_Real_Time_Data_Streaming.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

// Prepares a stats table for the records
public class SensorStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sensorId;

    private double avgValue;

    private double maxValue;

    private double minValue;
    // will be dealt when value will be entered through the frontend
    private double thresholdGivenByAdmin;

    private double deviationFromThreshold;

    private LocalDateTime recentUpdateTime;








}
