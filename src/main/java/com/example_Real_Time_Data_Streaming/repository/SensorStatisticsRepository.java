package com.example_Real_Time_Data_Streaming.repository;

import com.example_Real_Time_Data_Streaming.model.SensorStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface SensorStatisticsRepository extends JpaRepository<SensorStatistics, Long> {
    @Query(value=" select count(s) from SensorDataModel s where s.sensorId= : sensorId")

     void storeCountPerSensor(@Param("sensorId") String sensorId);

}
