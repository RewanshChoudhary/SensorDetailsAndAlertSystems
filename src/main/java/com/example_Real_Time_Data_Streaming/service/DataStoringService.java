package com.example_Real_Time_Data_Streaming.service;

import com.example_Real_Time_Data_Streaming.model.SensorDataModel;
import com.example_Real_Time_Data_Streaming.repository.SensorDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Slf4j
@Service
@RequiredArgsConstructor
public class
DataStoringService {
    private final SensorDataRepository sensorDataRepository;

    public void  saveDataToDB(String sensorId, String substanceName,  double value , String unit , double latitude, double longitude){
        GeometryFactory geometryFactory = new GeometryFactory();
        //Defining a point for lat and long access
        Point point=geometryFactory.createPoint(new Coordinate(longitude,latitude));
        //Required for Postgis
        point.setSRID(4326);


        SensorDataModel data= SensorDataModel.builder()
                .sensorId(sensorId)
                .substanceName(substanceName)
                .unit(unit)
                .value(value)
                .location(point)

                .timestamp(LocalDateTime.now())
                .build();


        //Saving the data to the DB
        sensorDataRepository.save(data);

        log.debug("Data was sent to DB");





    }
}
