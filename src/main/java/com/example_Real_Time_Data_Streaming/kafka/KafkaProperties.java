package com.example_Real_Time_Data_Streaming.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix="kafka")
@Data
public class KafkaProperties {
    private String bootstrapServers;
    private String topicName;
    private String groupId;



}
