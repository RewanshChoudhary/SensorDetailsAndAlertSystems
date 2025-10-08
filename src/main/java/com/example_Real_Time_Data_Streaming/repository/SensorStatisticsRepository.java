package com.example_Real_Time_Data_Streaming.repository;

import com.example_Real_Time_Data_Streaming.model.SensorStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



import org.springframework.data.jpa.repository.Modifying;

import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SensorStatisticsRepository extends JpaRepository<SensorStatistics, Long> {

    // ✅ COUNT query should return a numeric type
    @Query("SELECT COUNT(s) FROM SensorDataModel s WHERE s.sensorId = :sensorId")
    long getCountPerSensor(@Param("sensorId") String sensorId);

    // ✅ Updates must be annotated with @Modifying and @Transactional
    @Modifying
    @Transactional
    @Query("UPDATE SensorStatistics s SET s.maxValue = :newMax WHERE s.sensorId = :sensorId")
    void setMaxValue(@Param("sensorId") String sensorId, @Param("newMax") double newMax);

    // ✅ Use field name, not the entire entity
    @Query("SELECT MAX(s.value) FROM SensorDataModel s WHERE s.sensorId = :sensorId")
    Double getMaxValue(@Param("sensorId") String sensorId);

    @Query("SELECT MIN(s.value) FROM SensorDataModel s WHERE s.sensorId = :sensorId")
    Double getMinValue(@Param("sensorId") String sensorId);

    @Modifying
    @Transactional
    @Query("UPDATE SensorStatistics s SET s.minValue = :newMin WHERE s.sensorId = :sensorId")
    void setMinValue(@Param("sensorId") String sensorId, @Param("newMin") double newMin);

    @Modifying
    @Transactional
    @Query("UPDATE SensorStatistics s SET s.avgValue = :avgValue WHERE s.sensorId = :sensorId")
    void setAvgValue(@Param("sensorId") String sensorId, @Param("avgValue") double avgValue);

    @Modifying
    @Transactional
    @Query("UPDATE SensorStatistics s SET s.deviationFromThreshold = ABS(s.deviationFromThreshold - :value) WHERE s.sensorId = :sensorId")
    void setDeviationFromThreshold(@Param("value") double value, @Param("sensorId") String sensorId);
}
