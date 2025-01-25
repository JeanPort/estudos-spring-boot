package com.jean.controller;

import com.jean.commons.FileUtil;
import com.jean.domain.Anime;
import com.jean.repository.AnimeData;
import com.jean.repository.AnimeHardCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = AnimeController.class)
@ComponentScan(basePackages = "com.jean")
//@Import({AnimeMapperImpl.class, AnimeService.class, AnimeHardCodeRepository.class, AnimeData.class})
class AnimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String URL = "/v1/animes";

    @MockBean
    private AnimeData data;

    @SpyBean
    private AnimeHardCodeRepository repository;

    private List<Anime> animes;

    @Autowired
    private FileUtil fileUtil;

    @BeforeEach
    public void init() {
        animes = new ArrayList<>(List.of(Anime.builder().id(1L).name("Anime 1").build(),Anime.builder().id(2L).name("Anime 2").build(),Anime.builder().id(3L).name("Anime 3").build()));
    }

    @Test
    void findAll_ReturnAllAnimes_WhenArgumentIdNull() throws Exception {

        BDDMockito.when(data.getAnimes()).thenReturn(animes);
        var response = fileUtil.readResourceFile("anime/get-anime-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void findAll_ReturnAllAnimes_WhenArgumentIsFound() throws Exception {

        BDDMockito.when(data.getAnimes()).thenReturn(animes);
        var response = fileUtil.readResourceFile("anime/get-anime-anime1-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", animes.getFirst().getName()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void findAll_ListEmpty_WhenArgumentIsNotFound() throws Exception {

        BDDMockito.when(data.getAnimes()).thenReturn(animes);
        var response = fileUtil.readResourceFile("anime/get-anime-x-name-200.json");
        var name = "x";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void findById_ReturnAnime_WhenArgumentFound() throws Exception {

        BDDMockito.when(data.getAnimes()).thenReturn(animes);
        var response = fileUtil.readResourceFile("anime/get-anime-id-1-200.json");
        var id = animes.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.get(URL+"/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void findById_Exception_WhenArgumentNotFound() throws Exception {
        BDDMockito.when(data.getAnimes()).thenReturn(animes);
        var id = 10L;
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void  post_CreatedAnime_WhenSuccessful() throws Exception {

        var anime = Anime.builder().id(10L).name("Naruto").build();
        var request = fileUtil.readResourceFile("anime/post-request-anime-novo-200.json");
        var response = fileUtil.readResourceFile("anime/post-respose-anime-novo-200.json");
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(anime);

        mockMvc.perform(MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON).content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void  update_UpdatedAnime_WhenSuccessful() throws Exception {
        var request = fileUtil.readResourceFile("anime/update-anime-id-1-200.json");
        BDDMockito.when(data.getAnimes()).thenReturn(animes);

        mockMvc.perform(MockMvcRequestBuilders.put(URL).contentType(MediaType.APPLICATION_JSON).content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void  update_ReturnException_WhenArgumentNotFound() throws Exception {
        var request = fileUtil.readResourceFile("anime/update-anime-id-10-200.json");
        BDDMockito.when(data.getAnimes()).thenReturn(animes);

        mockMvc.perform(MockMvcRequestBuilders.put(URL).contentType(MediaType.APPLICATION_JSON).content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void  delete_ReturnException_WhenArgumentNotFound() throws Exception {
        var id = 10L;
        BDDMockito.when(data.getAnimes()).thenReturn(animes);

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void  delete_deleteAnime_WhenArgumentFound() throws Exception {
        var id = 1L;
        BDDMockito.when(data.getAnimes()).thenReturn(animes);

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}