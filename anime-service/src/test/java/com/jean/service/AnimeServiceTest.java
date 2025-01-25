package com.jean.service;

import com.jean.domain.Anime;
import com.jean.repository.AnimeHardCodeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@ExtendWith(MockitoExtension.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService service;

    @Mock
    private AnimeHardCodeRepository repository;

    private List<Anime> animes;

    private Anime animeExist;


    @BeforeEach
    public void init() {
        animes = new ArrayList<>(List.of(Anime.builder().id(1L).name("Anime 1").build(),Anime.builder().id(2L).name("Anime 2").build(),Anime.builder().id(3L).name("Anime 3").build()));
        animeExist = animes.getFirst();

    }

    @Test
    void findAll_ReturnAllListAnime_WhenArgumentIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(animes);
        List<Anime> responses = service.findAll(null);
        Assertions.assertThat(responses).isNotNull().hasSameElementsAs(animes);
    }

    @Test
    void findAll_ReturnistAnimeForName_WhenArgumentIsFound() {

        BDDMockito.when(repository.findByName(animeExist.getName())).thenReturn(animes.stream().filter(producer1 -> producer1.getName().equalsIgnoreCase(animeExist.getName())).toList());
        var responses = service.findAll(animeExist.getName());
        Assertions.assertThat(responses).contains(animeExist);
    }

    @Test
    void findAll_ReturnListEmpty_WhenArgumentIsNotFound() {

        var nome = "Não existe";
        BDDMockito.when(repository.findByName(nome)).thenReturn(emptyList());
        var responses = service.findAll(nome);
        Assertions.assertThat(responses).isNotNull().isEmpty();
    }

    @Test
    void findById_ReturnAnimeGetResponse_WhenArgumentIsFound() {

        BDDMockito.when(repository.findById(animeExist.getId())).thenReturn(Optional.of(animeExist));
        var res = service.findById(animeExist.getId());
        Assertions.assertThat(res).isNotNull().isEqualTo(animeExist);
    }

    @Test
    void findById_ReturnException_WhenArgumentIsNotFound() {
        Long id = 50L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.findById(id)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void save_ReturnAnimePostResponse_WhenSuccessful() {

        var savad = Anime.builder().id(4L).name("NOVO ANIME").build();


        BDDMockito.when(repository.save(savad)).thenReturn(savad);
        var response = service.save(savad);

        Assertions.assertThat(response).isEqualTo(savad);
    }

    @Test
    void delete_DeleteAnime_WhenSuccessful() {

        BDDMockito.when(repository.findById(animeExist.getId())).thenReturn(Optional.of(animeExist));
        BDDMockito.doNothing().when(repository).delete(animeExist);
        Assertions.assertThatNoException().isThrownBy(() -> service.delete(animeExist.getId()));
    }

    @Test
    void delete_ReturnException_WhenNotFound() {
        var id = 15L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.delete(id)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void update_ReturnException_WhenNotFound() {
        var updateAnime = Anime.builder().id(15L).name("NOvo nome").build();
        BDDMockito.when(repository.findById(updateAnime.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.update(updateAnime)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void update_UpdateAnime_WhenSuccessful() {

        var updateAnime = Anime.builder().id(animeExist.getId()).name("Atualizando nome").build();
        BDDMockito.when(repository.findById(animeExist.getId())).thenReturn(Optional.of(animeExist));
        BDDMockito.doNothing().when(repository).update(animeExist);
        Assertions.assertThatNoException().isThrownBy(() -> service.update(updateAnime));
    }

}