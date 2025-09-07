package com.example_Real_Time_Data_Streaming.kafka;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {
    private final KafkaProperties kafkaProperties;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Value("${spring.kafka.sampletopic}")
    private String topicname;

    @Value("${spring.kafka.samplegroupId}")
    private String groupId ;


    @KafkaListener(topics = "${spring.kafka.sampletopic}", groupId = "${spring.kafka.samplegroupId}")
    public void receiveKafkaMessage(ConsumerRecord<String,String> record ) {
        String key=record.key();
        String value =record.value();
        System.out.println("Received message: "+key+": "+value);
        simpMessagingTemplate.convertAndSend("/topic/sensor-data",key+":"+value);


    }
    @KafkaListener(topics="avg-val-of-each",groupId="groupid")
    public void getAverageValue(ConsumerRecord<String,String> record){
        String key=record.key();
        String value =record.value();
       System.out.println("Working "+key+": "+value);
    }


}
