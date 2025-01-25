package com.etf.config;

import com.etf.topology.Topology;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class StreamConfiguration {

    @Bean
    public NewTopic greetings(){
        return TopicBuilder.name(Topology.TRANSACTIONS)
                .partitions(100)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic greetingsUppercase(){
        return TopicBuilder.name(Topology.PROCESSED_TRANSACTION)
                .partitions(100)
                .replicas(1)
                .build();
    }
}
