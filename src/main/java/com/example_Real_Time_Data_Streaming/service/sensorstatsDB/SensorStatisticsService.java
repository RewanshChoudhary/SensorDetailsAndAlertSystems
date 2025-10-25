package com.example_Real_Time_Data_Streaming.service.sensorstatsDB;

import com.example_Real_Time_Data_Streaming.model.SensorStatistics;
import com.example_Real_Time_Data_Streaming.repository.SensorStatisticsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SensorStatisticsService {

    @Autowired
    private  final SensorStatisticsRepository sensorStatisticsRepository;

    public void checkAndChangeMaxSensorValue(String sensorId,double value){
        long count = sensorStatisticsRepository.getCountPerSensor(sensorId);

        if (count == 0) {
            // No record exists, create a new one
            SensorStatistics newStats = new SensorStatistics();
            newStats.setSensorId(sensorId);
            newStats.setMaxValue(value); // optional, set default values
            newStats.setMinValue(value); // optional, set default values
            newStats.setAvgValue(value); // optional, set default values
            newStats.setDeviationFromThreshold(value); // initial deviation
            sensorStatisticsRepository.save(newStats);
            return; // already set deviation, no need to call update
        }
        sensorStatisticsRepository.modifyMaxValue(sensorId,value);
    }

    public void checkAndChangeMinSensorValue(String sensorId,double value){
        long count = sensorStatisticsRepository.getCountPerSensor(sensorId);

        if (count == 0) {
            // No record exists, create a new one
            SensorStatistics newStats = new SensorStatistics();
            newStats.setSensorId(sensorId);
            newStats.setMaxValue(value); // optional, set default values
            newStats.setMinValue(value); // optional, set default values
            newStats.setAvgValue(value); // optional, set default values
            newStats.setDeviationFromThreshold(value); // initial deviation
            sensorStatisticsRepository.save(newStats);
            return; // already set deviation, no need to call update
        }
        sensorStatisticsRepository.modifyMinValue(sensorId,value);


    }

    public void changeAvgValue(String sensorId,double value){
        long count = sensorStatisticsRepository.getCountPerSensor(sensorId);

        if (count == 0) {
            // No record exists, create a new one
            SensorStatistics newStats = new SensorStatistics();
            newStats.setSensorId(sensorId);
            newStats.setMaxValue(value); // optional, set default values
            newStats.setMinValue(value); // optional, set default values
            newStats.setAvgValue(value); // optional, set default values
            newStats.setDeviationFromThreshold(value); // initial deviation
            sensorStatisticsRepository.save(newStats);
            return; // already set deviation, no need to call update
        }
        sensorStatisticsRepository.modifyAvgValue(sensorId,value);


    }

    public long getCountPerSensor(String sensorId){
             return sensorStatisticsRepository.getCountPerSensor(sensorId);
    }

    public void modifyDeviationFromThreshold(double value,String sensorId){
        long count = sensorStatisticsRepository.getCountPerSensor(sensorId);

        if (count == 0) {
            // No record exists, create a new one
            SensorStatistics newStats = new SensorStatistics();
            newStats.setSensorId(sensorId);
            newStats.setMaxValue(value); // optional, set default values
            newStats.setMinValue(value); // optional, set default values
            newStats.setAvgValue(value); // optional, set default values
            newStats.setDeviationFromThreshold(value); // initial deviation
            sensorStatisticsRepository.save(newStats);
            return; // already set deviation, no need to call update
        }
        sensorStatisticsRepository.modifyDeviationFromThreshold(value,sensorId);

    }

}
