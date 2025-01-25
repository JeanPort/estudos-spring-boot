package com.jean.service;

import com.jean.domain.Producer;
import com.jean.repository.ProducerHardCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerHardCodeRepository repository;

    
    public List<Producer> findAll(String name) {

        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public Producer findById(Long id) {
        return  repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));
    }

    public Producer save(Producer request) {

        return repository.save(request);

    }

    public void delete(Long id) {
        Producer res = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));
        repository.delete(res);
    }

    public void update(Producer request) {
        var res = repository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));
        request.setCreatedAt(res.getCreatedAt());
        repository.update(request);
    }

}
