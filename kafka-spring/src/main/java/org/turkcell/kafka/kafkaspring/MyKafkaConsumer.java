package org.turkcell.kafka.kafkaspring;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class MyKafkaConsumer {

    public static void main(String[] args) {
        Properties propertiesLoc = new Properties();
        propertiesLoc.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                          "127.0.0.1:9092,127.0.0.1:9093");
        propertiesLoc.put(ConsumerConfig.GROUP_ID_CONFIG,
                          "java-group-1");
        propertiesLoc.put(ConsumerConfig.CLIENT_ID_CONFIG,
                          args[0]);
        propertiesLoc.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG,
                          args[1]);

        propertiesLoc.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                          IntegerDeserializer.class.getName());
        propertiesLoc.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                          StringDeserializer.class.getName());
        propertiesLoc.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                          "earliest");
        propertiesLoc.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
                          "false");

        try (KafkaConsumer<Integer, String> kafkaConsumerLoc = new KafkaConsumer<>(propertiesLoc)) {
            kafkaConsumerLoc.subscribe(List.of("sms-topic"));
            while (true) {
                ConsumerRecords<Integer, String> polledDataLoc = kafkaConsumerLoc.poll(Duration.ofMillis(1000));
                if (polledDataLoc != null && !polledDataLoc.isEmpty()) {
                    for (ConsumerRecord<Integer, String> polledDatumLoc : polledDataLoc) {
                        System.out.println("received SMS : " + polledDatumLoc);
                    }
                }
                kafkaConsumerLoc.commitSync();
            }
        } catch (Exception exceptionParam) {

        }

    }

}
