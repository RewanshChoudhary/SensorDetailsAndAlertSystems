package com.example_Real_Time_Data_Streaming.service;
import com.example_Real_Time_Data_Streaming.model.AvgCount;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;

import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class PublishAverageToKafkaStreamService {
    public void publishToAnotherTopic(){
        StreamsBuilder builder=new StreamsBuilder();
        KStream<String,String> senDataStream=builder.stream("dataTopic");


        Serde<AvgCount> avgSerde = new JsonSerde<>(AvgCount.class);

        KGroupedStream<String,String> groupedBySensorId=senDataStream.groupByKey();
        TimeWindows tumblingWindow= TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(10));
        KTable<Windowed<String>, AvgCount> aggregated=groupedBySensorId
                .windowedBy(tumblingWindow)
                .aggregate(
                        () -> new AvgCount(0.0, 0L),
                        (key, value, agg) -> {
                            double message = Double.parseDouble(value);
                            return agg.add(message); // Return updated object
                        },
                        Materialized.with(Serdes.String(), new JsonSerde<>(AvgCount.class))
                );

        aggregated.toStream()
                .map((windowKey,agg)->{
                    String sensorId=windowKey.key();
                    double avg=agg.getAverage();
                    return KeyValue.pair(sensorId,avg);

                })
                .to("avg-val-of-each");




    }
}