package com.jean.repository;

import com.jean.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AnimeHardCodeRepositoryTest {

    @InjectMocks
    private AnimeHardCodeRepository repository;
    @Mock
    private AnimeData animeData;

    private List<Anime> animeList;

    @BeforeEach
    void init() {
        var anime1 = Anime.builder().id(1L).name("Anime Teste 1").build();
        var anime2 = Anime.builder().id(2L).name("Anime Teste 3").build();
        var anime3 = Anime.builder().id(3L).name("Anime Teste 3").build();

        animeList = new ArrayList<>(List.of(anime1, anime2, anime3));
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
    }

    @Test
    void findAll_ReturnListAnimes_WhenSuccessful() {
        var list = repository.findAll();
        Assertions.assertThat(list).isNotNull().hasSize(animeList.size());
    }

    @Test
    void findByName_ReturnListAnimesByName_WhenNameExist() {
        var animeSarch = animeList.getFirst();

        var res = repository.findByName(animeSarch.getName());
        Assertions.assertThat(res).contains(animeSarch);
    }

    @Test
    void findByName_ReturnListAnimeEmpty_WhenNameNotExist() {
        var res = repository.findByName(null);
        Assertions.assertThat(res).isNotNull().isEmpty();
    }

    @Test
    void findById_ReturnAnime_WhenSuccessful() {

        var anime = animeList.getFirst();
        Optional<Anime> res = repository.findById(anime.getId());
        Assertions.assertThat(res).isPresent().contains(anime);
    }

    @Test
    void save_SaveAnime_WhenSuccessful() {

        var newAnime = Anime.builder().id(12L).name("Novo anime").build();

        var res = repository.save(newAnime);
        Assertions.assertThat(res).isEqualTo(newAnime);

        var animeGet = repository.findById(newAnime.getId());
        Assertions.assertThat(animeGet).isPresent().contains(newAnime);
    }

    @Test
    void delete_DeleteAnime_WhenSuccessful() {
        var anime = animeList.getFirst();
        repository.delete(anime);
        Assertions.assertThat(this.animeList).doesNotContain(anime);
    }

    @Test
    void update_UpdateAnime_WhenSuccessful() {

        Anime update = animeList.getFirst();
        update.setName("Atualizando");
        repository.update(update);
        Assertions.assertThat(this.animeList).contains(update);

        Optional<Anime> res = repository.findById(update.getId());
        Assertions.assertThat(res).isPresent();
        Assertions.assertThat(res.get().getName()).isEqualTo(update.getName());
    }

}