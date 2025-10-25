package com.example_Real_Time_Data_Streaming.repository;

import com.example_Real_Time_Data_Streaming.model.SensorStatistics;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SensorStatisticsRepository extends JpaRepository<SensorStatistics, Long> {

    @Query("SELECT COUNT(s) FROM SensorDataModel s WHERE s.sensorId = :sensorId")
    long getCountPerSensor(@Param("sensorId") String sensorId);

    @Modifying
    @Transactional
    @Query("""
        UPDATE SensorStatistics s 
        SET s.maxValue = :newMax 
        WHERE s.sensorId = :sensorId 
        AND (:newMax > s.maxValue OR s.maxValue IS NULL)
    """)
    void modifyMaxValue(@Param("sensorId") String sensorId, @Param("newMax") double newMax);

    @Query("SELECT MAX(s.value) FROM SensorDataModel s WHERE s.sensorId = :sensorId")
    Double getMaxValue(@Param("sensorId") String sensorId);

    @Query("SELECT MIN(s.value) FROM SensorDataModel s WHERE s.sensorId = :sensorId")
    Double getMinValue(@Param("sensorId") String sensorId);

    @Modifying
    @Transactional
    @Query("""
        UPDATE SensorStatistics s 
        SET s.minValue = :newMin 
        WHERE s.sensorId = :sensorId 
        AND (:newMin < s.minValue OR s.minValue IS NULL)
    """)
    void modifyMinValue(@Param("sensorId") String sensorId, @Param("newMin") double newMin);

    @Modifying
    @Transactional
    @Query("""
        UPDATE SensorStatistics s 
        SET s.avgValue = :avgValue 
        WHERE s.sensorId = :sensorId
    """)
    void modifyAvgValue(@Param("sensorId") String sensorId, @Param("avgValue") double avgValue);

    @Modifying
    @Transactional
    @Query("""
        UPDATE SensorStatistics s 
        SET s.deviationFromThreshold = ABS(:value - s.deviationFromThreshold)
        WHERE s.sensorId = :sensorId
    """)
    void modifyDeviationFromThreshold(@Param("value") double value, @Param("sensorId") String sensorId);
}
