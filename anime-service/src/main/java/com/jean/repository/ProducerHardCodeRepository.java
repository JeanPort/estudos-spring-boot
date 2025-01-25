package com.jean.repository;

import com.jean.config.Connection;
import com.jean.domain.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProducerHardCodeRepository {
    
    private final ProducerData producerData;
    private final Connection connection;

    public List<Producer> findAll() {
        log.info("Connection {}",connection);
        return producerData.getProducers();
    }

    public List<Producer> findByName(String name) {
        return producerData.getProducers().stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();
    }

    public Optional<Producer> findById(Long id) {
        return producerData.getProducers().stream().filter(producer -> producer.getId().equals(id)).findFirst();
    }

    public Producer save(Producer producer) {
        producerData.getProducers().add(producer);
        return producer;
    }

    public void delete(Producer producer) {
        producerData.getProducers().remove(producer);
    }

    public void update(Producer producer) {
        delete(producer);
        save(producer);
    }
}
