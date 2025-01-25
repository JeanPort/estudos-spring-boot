package com.jean.controller;

import com.jean.mapper.AnimeMapper;
import com.jean.request.AnimePostRequest;
import com.jean.request.AnimePutRequest;
import com.jean.response.AnimeGetResponse;
import com.jean.response.AnimePostResponse;
import com.jean.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/animes")
public class AnimeController {

    private final AnimeService service;
    private final AnimeMapper MAPPER ;

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> findAll(@RequestParam(required = false) String name){
        var res = service.findAll(name);
        return ResponseEntity.ok(MAPPER.toListAnimeGetResponse(res));

    }



    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> buscaPorid(@PathVariable Long id){

        var response = service.findById(id);

        return ResponseEntity.ok(MAPPER.toAnimeGetResponse(response));
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest novoAnime) {

        var response = service.save(MAPPER.toAnime(novoAnime));
        return ResponseEntity.status(201).body(MAPPER.toAnimePostResponse(response));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request){

        service.update(MAPPER.toAnime(request));
        return ResponseEntity.noContent().build();
    }
}

