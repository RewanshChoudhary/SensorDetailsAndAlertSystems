package com.example_Real_Time_Data_Streaming.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SampleDataProvider {
    public String getSampleData() throws IOException {
        ObjectMapper mapper=new ObjectMapper();
        JsonNode rootArray=mapper.readTree(new File("sample.json"));
        int size=rootArray.size();


    }





}
