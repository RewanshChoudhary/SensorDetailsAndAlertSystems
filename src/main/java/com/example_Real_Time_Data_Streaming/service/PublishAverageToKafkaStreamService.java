package com.example_Real_Time_Data_Streaming.service;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;

import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class PublishAverageToKafkaStreamService {
    public void publishToAnotherTopic(){
        StreamsBuilder builder=new StreamsBuilder();
        KStream<String,String> senDataStream=builder.stream("dataTopic");
        KGroupedStream<String,String> groupedBySensorId=senDataStream.groupByKey();
        TimeWindows tumblingWindow= TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(10));
    }
}