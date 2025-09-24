package com.example_Real_Time_Data_Streaming.kafka;


import com.example_Real_Time_Data_Streaming.service.SensorStatisticsService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {



    private final SimpMessagingTemplate simpMessagingTemplate;

    private final SensorStatisticsService sensorStatisticsService;






    @KafkaListener(topics = "${spring.kafka.sampletopic}", groupId = "${spring.kafka.samplegroupId}")
    public void receiveKafkaMessage(ConsumerRecord<String,String> record ) {
        String key=record.key();
        String value =record.value();
        double sensorValue=Double.parseDouble(value);


        // changes max and min value in the stats table
        sensorStatisticsService.checkAndChangeMaxSensorValue(key,sensorValue);

        sensorStatisticsService.checkAndChangeMinSensorValue(key,sensorValue);





        System.out.println("Received message: "+key+": "+value);
        simpMessagingTemplate.convertAndSend("/topic/sensor-data",key+":"+value);


    }
    @KafkaListener(topics="avg-val-of-each",groupId="groupid")
    public void getAverageValue(ConsumerRecord<String,String> record){
        String key=record.key();
        String value =record.value();
        log.debug("Received message: "+key+": "+value);

        simpMessagingTemplate.convertAndSend("/topic/sensor-data-avg",key);
        log.debug("Works fine");

       System.out.println("Working "+key+": "+value);
    }

    @KafkaListener(topics="${spring.kafka.topic.alerttopic}",groupId = "groupid")
    public void sendAlertMessage(ConsumerRecord<String,String> record){
                           String key=record.key();
                           String value=record.value();
                       simpMessagingTemplate.convertAndSend("/topic/sensor-data-alert",key);

    }


}
