package com.jean.repository;

import com.jean.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeData {

    private final List<Anime> animes = new ArrayList<>(List.of(new Anime(1L, "One Peace"), new Anime(2L, "Naruto"),new Anime(3L, "DragonBall")));

    public List<Anime> getAnimes() {
        return animes;
    }
}
