package com.cawooka.producers;

import com.cawooka.Beagle;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class BeagleProducer {

    private Properties properties;
    private boolean result = false;
    public BeagleProducer() {
        properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("acks", "1");
        properties.setProperty("retries", "10");

        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", "http://127.0.0.1:8081");
    }

    public boolean publish(String name) {
        KafkaProducer<String, Beagle> producer = new KafkaProducer<String, Beagle>(properties);
        String topic = "beagle-test";

        Beagle beagle = Beagle.newBuilder()
                .setFirstName("Willow Louise")
                .setAge(2)
                .setLastName("Peachey")
                .build();

        ProducerRecord<String, Beagle> producerRecord = new ProducerRecord<String, Beagle>(topic, beagle);

        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e == null) {
                    result = true;
                    System.out.println(recordMetadata.toString());
                }
            }
        });

        return result;
    }
}
