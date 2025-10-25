package com.example_Real_Time_Data_Streaming.service.sensorstatsDB;

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
        sensorStatisticsRepository.modifyMaxValue(sensorId,value);
    }

    public void checkAndChangeMinSensorValue(String sensorId,double value){
        sensorStatisticsRepository.modifyMinValue(sensorId,value);


    }

    public void changeAvgValue(String sensorId,double value){
        sensorStatisticsRepository.modifyAvgValue(sensorId,value);


    }

    public long getCountPerSensor(String sensorId){
             return sensorStatisticsRepository.getCountPerSensor(sensorId);
    }

    public void modifyDeviationFromThreshold(double value,String sensorId){
        sensorStatisticsRepository.modifyDeviationFromThreshold(value,sensorId);

    }

}
