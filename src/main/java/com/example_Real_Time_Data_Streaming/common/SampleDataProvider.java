package com.example_Real_Time_Data_Streaming.common;

import com.example_Real_Time_Data_Streaming.repository.SensorDataRepository;
import com.example_Real_Time_Data_Streaming.service.DataSendingAndSavingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;

import java.util.Random;
@Slf4j
@Service
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class SampleDataProvider {
    private final DataSendingAndSavingService dataSendingAndSavingService;




    public String getSampleData() throws IOException {
        ObjectMapper mapper=new ObjectMapper();
        JsonNode rootArray=mapper.readTree(new File("src/main/java/com/example_Real_Time_Data_Streaming/common/sample.json"));
        int size=rootArray.size();
        int ranInd=new Random().nextInt(size);

        // Accessing values by defining a json node
        JsonNode ranSample=rootArray.get(ranInd);
        JsonNode location=ranSample.get("location");
        double lat=location.get("lat").doubleValue();
        double lon=location.get("lon").doubleValue();
        log.debug("Trying to work on parsing json file");


        double val=Double.parseDouble(ranSample.get("value").asText());
        //Service method to save data called here
        dataSendingAndSavingService.saveDataToDB(ranSample.get("sensor_id").asText(),ranSample.get("harmful_substance").asText(),val,ranSample.get("unit").asText(),lat,lon);




        return ranSample.get("sensor_id")+","+ranSample.get("value");






    }






}
