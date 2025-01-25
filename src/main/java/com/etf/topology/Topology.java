package com.etf.topology;


import com.etf.entities.DTO.ProcessesTransactionDTO;
import com.etf.serdes.ProcessedTransactionSerde;
import com.etf.serdes.TransactionSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Topology {
    public static String TRANSACTIONS = "Transaction";
    public static String PROCESSED_TRANSACTION = "ProcessedTransaction";
    @Autowired
    public void process(StreamsBuilder streamsBuilder) {
//        Materialized<String, CumulativeData, KeyValueStore<Bytes, byte[]>> stateStore = Materialized
//                .<String, CumulativeData>as("cumulative-data-store")
//                .withKeySerde(Serdes.String())
//                .withValueSerde(new CumulativeDataSerde());
//
//        streamsBuilder.stream(TRANSACTIONS, Consumed.with(Serdes.String(), new TransactionSerde()))
//                .groupByKey()
//                .aggregate(
//                        CumulativeData::new, // Initializer
//                        (key, transaction, cumulative) -> cumulative.add(transaction),
//                        stateStore
//                )
//                .toStream()
//                .mapValues(cumulative -> {
//                    System.out.println("Average: " + cumulative.getAverage());
//                    System.out.println("Total: " + cumulative.getSum());
//                    return new ProcessesTransactionDTO(
//                            cumulative.getLatestTransaction(),
//                            String.valueOf(cumulative.getSum()),
//                            String.valueOf(cumulative.getAverage()),
//                            cumulative.getOrigin(),
//                            System.currentTimeMillis() - cumulative.getLatestTransaction().getBeginningTimestamp()
//                    );
//                })
//                .to(PROCESSED_TRANSACTION, Produced.with(Serdes.String(), new ProcessedTransactionSerde()));

        Map<String, Double> cumulativeData = new HashMap<>();
        cumulativeData.put("sum", 0.0);
        cumulativeData.put("count", 0.0);
        streamsBuilder
                .stream(TRANSACTIONS,
                        Consumed.with(Serdes.String(),
                                new TransactionSerde())
                ).mapValues(transaction -> {
                    double newSum = cumulativeData.get("sum") + transaction.getAmount().doubleValue();
                    double newCount = cumulativeData.get("count") + 1;
                    cumulativeData.put("sum", newSum);
                    cumulativeData.put("count", newCount);
                    double average = newSum / newCount;
                    return new ProcessesTransactionDTO(
                            transaction,
                            String.valueOf(newSum),
                            String.valueOf(average),
                            transaction.getOrigin(),
                            System.currentTimeMillis() - transaction.getBeginningTimestamp()
                    );
                }).to(PROCESSED_TRANSACTION,
                        Produced.with(Serdes.String(),new ProcessedTransactionSerde()));;
    }
}