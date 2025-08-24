package com.example_Real_Time_Data_Streaming.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.IOException;

import java.util.Random;

public class SampleDataProvider {
    public String getSampleData() throws IOException {
        ObjectMapper mapper=new ObjectMapper();
        JsonNode rootArray=mapper.readTree(new File("src/main/java/com/example_Real_Time_Data_Streaming/common/sample.json"));
        int size=rootArray.size();
        int ranInd=new Random().nextInt(size);
        JsonNode ranSample=rootArray.get(ranInd);


        return ranSample.get("sensor_id")+","+ranSample.get("value");






    }






}
