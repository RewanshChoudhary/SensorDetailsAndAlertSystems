package com.example_Real_Time_Data_Streaming.kafka;
import com.example_Real_Time_Data_Streaming.model.AvgCount;

import com.example_Real_Time_Data_Streaming.repository.SensorStatisticsRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PublishAverageToKafkaStreamService {
    private final SensorStatisticsRepository sensorStatisticsRepository;

    @Value(value="${spring.kafka.avgstreamtopic}")
    private String topicname;
   @Value(value="${spring.kafka.topic.alerttopic}")
    private String alerttopic;

//    @Value(value="${dummy.threshold}")
    private double threshold=90;

     @Bean
    public KStream<String,String > processStream(StreamsBuilder builder) {
         // Forming the sensor stream
        KStream<String,String> sensorDataStream=builder.stream(topicname,
                Consumed.with(Serdes.String(), Serdes.String()));
        sensorDataStream.filter(
                (key,value)->{
                    String [] data=value.split(",");
                    var  reading=Double.parseDouble(data[0]);


                    return reading>threshold;
                }
        )        // Sending to alert topic majorly for frontend  and remember to add serde as by default it is not added
                .to(alerttopic,Produced.with(Serdes.String(), Serdes.String()));



        KGroupedStream<String,String> groupedBySensorId=sensorDataStream.groupByKey();
        //Creating tumbling window of duration  = ?
        TimeWindows tumblingWindow= TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(20));
        KTable<Windowed<String>, AvgCount> aggregated=groupedBySensorId
                .windowedBy(tumblingWindow)
                // Isolating different results
                .aggregate(
                        () -> new AvgCount(0.0, 0L),
                        (key, value, agg) -> {
                            String [] data=value.split(",");
                            String reading=data[1];

                            double message = Double.parseDouble(reading);
                            return agg.add(message); // Return updated object
                        },

                        //Deserialise and serialise according to the model class AVGCOUNT
                        Materialized.with(Serdes.String(), new JsonSerde<>(AvgCount.class))
                );

        aggregated.toStream()
                .map((windowKey,agg)->{
                    String sensorId=windowKey.key();
                    double avg=agg.getAverage();
                    sensorStatisticsRepository.modifyDeviationFromThreshold(avg,sensorId);

                    return KeyValue.pair(sensorId,avg);

                })
                .to("avg-val-of-each");




        return sensorDataStream;


    }
}