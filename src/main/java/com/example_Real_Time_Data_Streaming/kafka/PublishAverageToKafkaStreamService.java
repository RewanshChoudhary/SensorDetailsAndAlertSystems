package com.example_Real_Time_Data_Streaming.kafka;
import com.example_Real_Time_Data_Streaming.model.AvgCount;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

@Configuration
@EnableKafkaStreams
public class PublishAverageToKafkaStreamService {
    @Value(value="${spring.kafka.sampletopic}")
    private String topicname;
     @Bean
    public KStream<String,String > processStream(StreamsBuilder builder) {

        KStream<String,String> sensorDataStream=builder.stream(topicname,
                Consumed.with(Serdes.String(), Serdes.String()));
        Serde<AvgCount> avgSerde = new JsonSerde<>(AvgCount.class);

        KGroupedStream<String,String> groupedBySensorId=sensorDataStream.groupByKey();
        TimeWindows tumblingWindow= TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(20));
        KTable<Windowed<String>, AvgCount> aggregated=groupedBySensorId
                .windowedBy(tumblingWindow)
                .aggregate(
                        () -> new AvgCount(0.0, 0L),
                        (key, value, agg) -> {
                            String [] data=value.split(",");
                            String reading=data[1];

                            double message = Double.parseDouble(reading);
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


        return sensorDataStream;


    }
}