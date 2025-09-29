package com.fintrack.backend.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Produced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

/**
 * Kafka Streams configuration and lifecycle management.
 *
 * - Configures Kafka Streams properties
 * - Defines a simple topology (input-topic -> output-topic)
 * - Manages start/stop automatically with Spring lifecycle
 */
@Configuration
open class KafkaStreamsConfig {

    private lateinit var kafkaStreams: KafkaStreams

    @Bean
    open fun kafkaStreams(): KafkaStreams {
        val props = Properties().apply {
            put(StreamsConfig.APPLICATION_ID_CONFIG, "my-kafka-streams-app")
            put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde::class.java)
            put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde::class.java)
        }

        val builder = StreamsBuilder()

        // Example: forward from input-topic → output-topic
        builder.stream("input-topic", Consumed.with(Serdes.String(), Serdes.String()))
            .to("output-topic", Produced.with(Serdes.String(), Serdes.String()))

        kafkaStreams = KafkaStreams(builder.build(), props)
        return kafkaStreams
    }

    @PostConstruct
    fun start() {
        kafkaStreams.start()
        println("✅ Kafka Streams started")
    }

    @PreDestroy
    fun stop() {
        kafkaStreams.close()
        println("🛑 Kafka Streams stopped")
    }
}
