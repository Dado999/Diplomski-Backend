package com.etf.config;

import com.etf.entities.AccountType;
import com.etf.entities.DTO.*;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }
    @Bean
    public KafkaTemplate<String, Object> accountDTOKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfig()));
    }
//    @Bean
//    public KafkaTemplate<String, AccountTypeDTO> accountTypeDTOKafkaTemplate() {
//        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfig()));
//    }
//    @Bean
//    public KafkaTemplate<String, BankDTO> bankDTOKafkaTemplate() {
//        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfig()));
//    }
//    @Bean
//    public KafkaTemplate<String, TransactionDTO> transactionDTOKafkaTemplate() {
//        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfig()));
//    }
//    @Bean
//    public KafkaTemplate<String, TransactionLogDTO> transactionLogDTOKafkaTemplate() {
//        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfig()));
//    }
//    @Bean
//    public KafkaTemplate<String, TransactionStatusDTO> transactionStatusDTOKafkaTemplate() {
//        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfig()));
//    }
//    @Bean
//    public KafkaTemplate<String, UsersDTO> usersDTOKafkaTemplate() {
//        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfig()));
//    }
//    @Bean
//    public KafkaTemplate<String, WorkerDTO> workerDTOKafkaTemplate() {
//        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfig()));
//    }
}
