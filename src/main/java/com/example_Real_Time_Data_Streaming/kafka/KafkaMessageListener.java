package com.example_Real_Time_Data_Streaming.kafka;

import lombok.RequiredArgsConstructor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service

@RequiredArgsConstructor
public class KafkaMessageListener {
    private final KafkaProperties kafkaProperties;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Value("${kafka.sampletopic}")
    private String topicname;

    @Value("${kafka.samplegroupId}")
    private String groupId ;


    @KafkaListener(topics = "${kafka.sampletopic}", groupId = "${kafka.samplegroupId}")
    public void receiveKafkaMessage(ConsumerRecord<String,String> record ) {
        String key=record.key();
        String value =record.value();
        System.out.println("Received message: "+key+": "+value);
        simpMessagingTemplate.convertAndSend("/topic/sensor-data",key+":"+value);


    }

}
