package com.app.service;

import akka.actor.ClassicActorSystemProvider;
import akka.kafka.ProducerSettings;
import akka.kafka.javadsl.SendProducer;
import com.app.model.EventBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class KafkaService {

    private final Config config;
    private final ProducerSettings<String, String> producerSettings;

    private final SendProducer<String, String> kafkaProducer;

    public KafkaService(ClassicActorSystemProvider system) {
        this.config = system.classicSystem().settings().config().getConfig("akka.kafka.producer");
        this.producerSettings = ProducerSettings.create(config, new StringSerializer(), new StringSerializer())
                .withBootstrapServers("localhost:9092");
        this.kafkaProducer = new SendProducer<>(producerSettings, system);
    }

    public CompletionStage<String> pushLogs(EventBody eventBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            kafkaProducer.send(new ProducerRecord<>(eventBody.getType(), objectMapper.writeValueAsString(eventBody)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture("success");
    }
}
