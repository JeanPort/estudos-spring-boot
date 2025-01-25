package com.jean.controller;

import com.jean.config.Connection;
import com.jean.domain.Producer;
import com.jean.mapper.ProducerMapper;
import com.jean.request.ProducerPostRequest;
import com.jean.request.ProducerPutRequest;
import com.jean.response.ProducerGetResponse;
import com.jean.response.ProducerPostResponse;
import com.jean.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/producers")
@RequiredArgsConstructor
@Slf4j
public class ProducerController {

    private final ProducerService service;
    private final ProducerMapper MAPPER;



    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> findAll(@RequestParam(required = false) String name){

        var responses = service.findAll(name);
        var res = MAPPER.toListProducerGetResponse(responses);
        return ResponseEntity.ok(res);

    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerGetResponse> buscaPorid(@PathVariable Long id){
        var res = service.findById(id);
        return ResponseEntity.ok(MAPPER.toProducerGetResponse(res));
    }

    @PostMapping
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest request) {
        Producer producer = MAPPER.toProducer(request);
        var res = service.save(producer);
        return ResponseEntity.status(HttpStatus.CREATED).body(MAPPER.toProducerPostResponse(res));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
         service.delete(id);
         return ResponseEntity.noContent().build();
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
        var producer = MAPPER.toProducer(request);
        service.update(producer);
        return ResponseEntity.noContent().build();
    }
}
