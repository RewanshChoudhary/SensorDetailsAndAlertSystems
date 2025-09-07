package com.example_Real_Time_Data_Streaming.service;

import com.example_Real_Time_Data_Streaming.common.SampleDataProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Slf4j
public class DataSendingService {
    private final KafkaTemplate kafkaTemplate;


  private final SampleDataProvider sampleDataProvider;

    @Value("${spring.kafka.sampletopic}")
    private String topicName;

 //Repeats after a sec everytime
    @Scheduled(initialDelay=5000,fixedDelay = 10000)

    public void scheduledDataSupply() throws IOException {
        // Receives a chain of  data from this class

        log.debug("scheduledDataSupply");
        String data = sampleDataProvider.getSampleData();
        //dataList[0]=id && dataList[1]=value

        String[] dataList = data.split(",");


        //Sending to kafka topic mentioned inside kafkaProperties

        kafkaTemplate.send(topicName,dataList[0],data);






    }
    //Saving the data with extra data fields to The DB

}
