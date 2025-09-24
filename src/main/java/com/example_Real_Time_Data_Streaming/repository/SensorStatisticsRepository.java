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

    @Query(value="update SensorStatistics s set s.maxValue= :newMax where s.sensorId=:sensorId")
    void setMaxValue(@Param("sensorId") String sensorId,@Param("newMax") double newMax);

    @Query(value="select max(s) from SensorDataModel s where s.sensorId= :sensorId")
    double getMaxValue(@Param("sensorId") String sensorId);

    @Query(value="select min(s) from SensorDataModel s where s.sensorId= :sensorId")
    double getMinValue(@Param("sensorId") String sensorId);

    @Query(value="update SensorStatistics s set s.minValue= :newMin where s.sensorId=:sensorId")
    void setMinvalue(@Param("sensorId") String sensorId,@Param("newMin") double newMin);






}
