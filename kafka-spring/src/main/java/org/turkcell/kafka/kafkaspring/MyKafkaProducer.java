package org.turkcell.kafka.kafkaspring;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class MyKafkaProducer {

    public static void main(String[] args) {
        Properties propertiesLoc = new Properties();
        propertiesLoc.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                          "127.0.0.1:9092,127.0.0.1:9093");
        propertiesLoc.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                          IntegerSerializer.class.getName());
        propertiesLoc.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                          StringSerializer.class.getName());

        try (KafkaProducer<Integer, String> kafkaConsumerLoc = new KafkaProducer<>(propertiesLoc)) {
            for (int i = 0; i < 1_000_000; i++) {
                kafkaConsumerLoc.send(new ProducerRecord<>("sms-topic", "osman" + i));
                try {
                    Thread.sleep(1000);
                } catch (Exception exp) {
                }

            }
        } catch (Exception exceptionParam) {

        }

    }

}
