package com.example_Real_Time_Data_Streaming.service;

import com.example_Real_Time_Data_Streaming.repository.SensorStatisticsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SensorStatisticsService {

    @Autowired
    private  final SensorStatisticsRepository sensorStatisticsRepository;





    public void checkAndChangeMaxSensorValue(String sensorId,double value){
        if (value>sensorStatisticsRepository.getMaxValue(sensorId)){
            sensorStatisticsRepository.setMaxValue(sensorId,value);


        }
    }

    public void checkAndChangeMinSensorValue(String sensorId,double value){
        if (value<sensorStatisticsRepository.getMinValue(sensorId)){
            sensorStatisticsRepository.setMinvalue(sensorId,value);
        }
    }

}
