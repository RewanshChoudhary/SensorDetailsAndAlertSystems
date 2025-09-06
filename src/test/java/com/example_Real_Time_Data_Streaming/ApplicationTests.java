package com.example_Real_Time_Data_Streaming;


import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer

@Testcontainers
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApplicationTests {
    @Container
    private static KafkaContainer kafka = new KafkaContainer("apache/kafka-native:3.8.0");
public void setKafkaProperties()
}
