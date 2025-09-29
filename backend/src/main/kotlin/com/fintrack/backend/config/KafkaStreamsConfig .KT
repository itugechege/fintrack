package com.fintrack.backend.configuration


import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.Topology
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.Properties

/**
 * KafkaStreamsConfig sets up the Kafka Streams instance.
 * 
 * This configuration defines:
 * 1. Kafka connection properties (bootstrap servers, application ID, serdes)
 * 2. Stream topology (source and destination topics)
 */
@Configuration
class KafkaStreamsConfig {

    /**
     * Creates a KafkaStreams instance with a simple stream topology.
     * 
     * @return KafkaStreams instance which can be started manually
     */
    @Bean
    fun kafkaStreams(): KafkaStreams {
        // 1. Kafka Streams configuration properties
        val props = Properties().apply {
            // Unique ID for this Kafka Streams application
            put(StreamsConfig.APPLICATION_ID_CONFIG, "my-kafka-streams-app")
            // Address of the Kafka broker
            put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            // Default serializer/deserializer for keys
            put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, org.apache.kafka.common.serialization.Serdes.StringSerde::class.java)
            // Default serializer/deserializer for values
            put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, org.apache.kafka.common.serialization.Serdes.StringSerde::class.java)
        }

        // 2. Build the stream topology
        val builder = StreamsBuilder()

        /**
         * Example stream:
         * Reads messages from 'input-topic' and writes them to 'output-topic'.
         * Consumed.with and Produced.with specify Serdes for key/value.
         */
        val textLines: KStream<String, String> = builder.stream(
            "input-topic", 
            Consumed.with(org.apache.kafka.common.serialization.Serdes.String(), org.apache.kafka.common.serialization.Serdes.String())
        )

        // Forward the messages to the output topic
        textLines.to(
            "output-topic", 
            Produced.with(org.apache.kafka.common.serialization.Serdes.String(), org.apache.kafka.common.serialization.Serdes.String())
        )

        // 3. Build the topology
        val topology: Topology = builder.build()

        // 4. Create KafkaStreams instance with the topology and configuration
        return KafkaStreams(topology, props)
    }
}