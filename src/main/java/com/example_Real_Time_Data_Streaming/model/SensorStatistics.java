package com.example_Real_Time_Data_Streaming.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
// Prepares a stats table for the records
public class SensorStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sensorName;

    private double avgValue;

    private double maxValue;

    private double minValue;

    private double thresholdGivenByUser;

    private double deviationFromThreshold;

    private LocalDateTime recentUpdateTime;








}
