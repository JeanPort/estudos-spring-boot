package com.jean.repository;

import com.jean.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProducerData {

    private final List<Producer> producers = new ArrayList<>(List.of(new Producer(1L, "Mappa", LocalDateTime.now()), new Producer(2L, "Kyoto",LocalDateTime.now()),new Producer(3L, "StanLee", LocalDateTime.now())));

    public List<Producer> getProducers() {
        return producers;
    }
}
