package com.jean.service;

import com.jean.domain.Anime;
import com.jean.repository.AnimeHardCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AnimeService {

    private  final AnimeHardCodeRepository repository;

    public List<Anime> findAll(String name) {

        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public Anime findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));

    }

    public Anime save(Anime request) {
        return repository.save(request);
    }

    public void delete(Long id) {
        Anime res = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));
        repository.delete(res);
    }

    public void update(Anime request) {
        var res = repository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));
        repository.update(request);
    }

}
