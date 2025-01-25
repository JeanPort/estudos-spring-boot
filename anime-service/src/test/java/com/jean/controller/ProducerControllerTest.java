package com.jean.controller;

import com.jean.commons.FileUtil;
import com.jean.commons.ListUtil;
import com.jean.domain.Producer;
import com.jean.repository.ProducerData;
import com.jean.repository.ProducerHardCodeRepository;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(controllers = ProducerController.class)
@ComponentScan(basePackages = "com.jean")
//@Import({ProducerMapperImpl.class, ProducerService.class, ProducerHardCodeRepository.class, ProducerData.class})
class ProducerControllerTest {

    private static final String URL = "/v1/producers";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProducerData data;
    @SpyBean
    private ProducerHardCodeRepository repository;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private ListUtil listUtil;
    private List<Producer> list;


    @BeforeEach
    void init() {
        list = listUtil.getListProducer();
    }

    @Test
    void findAll_ReturnAllProducers_WhenArgumentIdNull() throws Exception {

        BDDMockito.when(data.getProducers()).thenReturn(list);
        var response = fileUtil.readResourceFile("producer/get-producer-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    void findAll_ReturnAProducers_WhenArgumentFound() throws Exception {

        BDDMockito.when(data.getProducers()).thenReturn(list);
        var response = fileUtil.readResourceFile("producer/get-producer-Teste1-name-200.json");
        var name = "Teste 1";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    void findAll_ReturnAllProducers_WhenArgumenNotFound() throws Exception {

        BDDMockito.when(data.getProducers()).thenReturn(list);
        var response = fileUtil.readResourceFile("producer/get-producer-x-name-200.json");
        var name = "x";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    void findById_ReturnAProducers_WhenSuccessFull() throws Exception {

        BDDMockito.when(data.getProducers()).thenReturn(list);
        var response = fileUtil.readResourceFile("producer/get-producer-by-id-1-200.json");
        var id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    void findById_ReturnAProducers_WhenArgumentNotFound() throws Exception {

        BDDMockito.when(data.getProducers()).thenReturn(list);

        var id = 8L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void save_CreatedProducers_WhenSuccessFull() throws Exception {


        var request = fileUtil.readResourceFile("producer/post-request-producer-200.json");
        var response = fileUtil.readResourceFile("producer/post-response-producer-201.json");
        var saved = Producer.builder().id(99L).name("Novo producer").createdAt(LocalDateTime.parse("2025-01-23T09:05:55.5063595")).build();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(saved);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    void update_UpdateProducers_WhenSuccessFull() throws Exception {

        BDDMockito.when(data.getProducers()).thenReturn(list);
        var request = fileUtil.readResourceFile("producer/put-request-producer-id-1-200.json");


        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void update_ReturnException_WhenArgumentNotFound() throws Exception {

        BDDMockito.when(data.getProducers()).thenReturn(list);
        var request = fileUtil.readResourceFile("producer/put-request-producer-id-10-200.json");


        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void delete_DeleteProducer_WhenSuccessful() throws Exception {

        var id = 1L;

        BDDMockito.when(data.getProducers()).thenReturn(list);

        mockMvc.perform(delete(URL+"/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void delete_ReturnException_WhenArgumentNotFound() throws Exception {

        var id = 19L;

        BDDMockito.when(data.getProducers()).thenReturn(list);

        mockMvc.perform(delete(URL+"/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}