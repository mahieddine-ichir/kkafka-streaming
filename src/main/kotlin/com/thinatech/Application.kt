package com.thinatech

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.EnableKafkaStreams

@SpringBootApplication
@EnableKafkaStreams
open class Application {

  /*
  // either define such a bean or declare spring.kafka properties (see application.properties)
  @Bean(name = [DEFAULT_STREAMS_CONFIG_BEAN_NAME])
  open fun kafkaConfig(): KafkaStreamsConfiguration {
    return mapOf(
      StreamsConfig.APPLICATION_ID_CONFIG to "kkafka-streams",
      StreamsConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092"
    ).let { KafkaStreamsConfiguration(it) }
  }
  */

  @Bean
  open fun stream(streamsBuilder: StreamsBuilder): InitializingBean {
    streamsBuilder.stream("test", Consumed.with(Serdes.String(), Serdes.String()))
      .peek{_, value -> println(value) }
      .to("output")

    streamsBuilder.build().apply { println(this.describe()) }

    return InitializingBean {  }
  }
}

fun main(args: Array<String>) {
  runApplication<Application>(*args)
}
