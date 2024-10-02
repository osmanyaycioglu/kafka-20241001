package org.turkcell.kafka.kafkaspring;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Properties;

public class MyKafkaStream {
    public static void main(String[] args) {
        Properties propertiesLoc = new Properties();
        propertiesLoc.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
                          "127.0.0.1:9092,127.0.0.1:9093");
        propertiesLoc.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                          Serdes.Integer()
                                .getClass());
        propertiesLoc.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                          Serdes.String()
                                .getClass());
        propertiesLoc.put(StreamsConfig.CLIENT_ID_CONFIG,
                          "client-1");
        propertiesLoc.put(StreamsConfig.APPLICATION_ID_CONFIG,
                          "my-app-stream");
        propertiesLoc.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG,
                          "instance-1");
        propertiesLoc.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                          "earliest");
        StreamsBuilder           streamsBuilderLoc = new StreamsBuilder();
        KStream<Integer, String> streamLoc         = streamsBuilderLoc.stream("sms-topic");

        streamLoc.mapValues(v -> v + " stream başında eklendi")
                 .filter((k, v) -> !v.contains("13"))
                 .foreach((k, v) -> System.out.println("Gelen value : " + v));
        streamLoc.peek((k, v) -> System.out.println("Another process : " + v))
                 .mapValues(v -> v + " another process")
                 .to("filtered-sms-topic",
                     Produced.with(Serdes.Integer(),
                                   Serdes.String()));
        Topology topologyLoc = streamsBuilderLoc.build();
        KafkaStreams kafkaStreamsLoc = new KafkaStreams(topologyLoc,
                                                        propertiesLoc);
        kafkaStreamsLoc.start();

        Runtime.getRuntime()
               .addShutdownHook(new Thread(() -> {
                   System.out.println("Sistem kapanıyor");
                   kafkaStreamsLoc.close();
               }));
    }
}
