package com.example_Real_Time_Data_Streaming.service;

import com.example_Real_Time_Data_Streaming.common.SampleDataProvider;
import com.example_Real_Time_Data_Streaming.kafka.KafkaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledDataSupplyService {
    private final KafkaTemplate kafkaTemplate;
    private final KafkaProperties kafkaProperties;
    @Value("${kafka.sampletopic}")
    private String topicName;

 //Repeats after a sec everytime
    @Scheduled(fixedRate = 1000)

    public void scheduledDataSupply() throws IOException {
        // Receives a chain of  data from this class
        SampleDataProvider sampleDataProvider = new SampleDataProvider();
        log.debug("scheduledDataSupply");
        String data = sampleDataProvider.getSampleData();
        //dataList[0]=id && dataList[1]=value

        String[] dataList = data.split(",");


        //Sending to kafka topic mentioned inside kafkaProperties

        kafkaTemplate.send(topicName,dataList[0],dataList[1] );



    }
}
