package com.jean.service;

import com.jean.domain.Producer;
import com.jean.repository.ProducerHardCodeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@ExtendWith(MockitoExtension.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService service;
    @Mock
    private ProducerHardCodeRepository repository;

    private List<Producer> producers;


    @BeforeEach
    void init() {
        var prod1 = Producer.builder().id(1L).name("Teste 1").createdAt(LocalDateTime.now()).build();
        var prod2 = Producer.builder().id(2L).name("Teste 2").createdAt(LocalDateTime.now()).build();
        var prod3 = Producer.builder().id(3L).name("Teste 3").createdAt(LocalDateTime.now()).build();
        producers = new ArrayList<>(List.of(prod1, prod2, prod3));

    }

    @Test
    void findAll_ReturnAllListProducer_WhenArgumentIsNull() {

        BDDMockito.when(repository.findAll()).thenReturn(producers);

        var responses = service.findAll(null);
        Assertions.assertThat(responses).isNotNull().hasSameElementsAs(producers);
    }

    @Test
    void findAll_ReturnAllListProducerByName_WhenArgumentIsFound() {

        var producer = producers.getFirst();

        BDDMockito.when(repository.findByName(producer.getName())).thenReturn(producers.stream().filter(producer1 -> producer1.getName().equalsIgnoreCase(producer.getName())).toList());

        var responses = service.findAll(producer.getName());
        Assertions.assertThat(responses).contains(producer);
    }

    @Test
    void findAll_ReturnListEmpty_WhenArgumentIsNotFound() {

        var notFound = "não existe";
        BDDMockito.when(repository.findByName(notFound)).thenReturn(emptyList());

        var responses = service.findAll(notFound);
        Assertions.assertThat(responses).isNotNull().isEmpty();
    }

    @Test
    void findByID_ReturnProducer_WhenSuccessful() {

        var expected = producers.getFirst();
        BDDMockito.when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

        var responses = service.findById(expected.getId());
        Assertions.assertThat(responses).isEqualTo(expected);
    }

    @Test
    void findByID_ReturnException_WhenIdNotFound() {

        var expected = producers.getFirst();
        BDDMockito.when(repository.findById(expected.getId())).thenReturn(Optional.empty());


        Assertions.assertThatException().isThrownBy(() -> service.findById(expected.getId())).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void save_ReturnProducerSaved_WhenSuccessful() {

        var novoPro = Producer.builder().id(10L).name("Novo Producer").createdAt(LocalDateTime.now()).build();

        BDDMockito.when(repository.save(novoPro)).thenReturn(novoPro);

        var res = service.save(novoPro);

        Assertions.assertThat(res).isEqualTo(novoPro);

    }

    @Test
    void update_UpdateProducer_WhenSuccessful() {
        Producer producer = producers.getFirst();
        var update = Producer.builder().id(producer.getId()).name("Atualizando nome").build();

        BDDMockito.when(repository.findById(update.getId())).thenReturn(Optional.of(producer));
        BDDMockito.doNothing().when(repository).update(producer);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(update));
        Assertions.assertThat(update.getCreatedAt()).isEqualTo(producer.getCreatedAt());

    }

    @Test
    void update_ReturnException_WhenIdIsNotFound() {
        Producer producer = producers.getFirst();

        BDDMockito.when(repository.findById(producer.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.update(producer)).isInstanceOf(ResponseStatusException.class);

    }

    @Test
    void delete_DeleteProducer_WhenSuccessful() {
        Producer producer = producers.getFirst();


        BDDMockito.when(repository.findById(producer.getId())).thenReturn(Optional.of(producer));
        BDDMockito.doNothing().when(repository).delete(producer);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(producer.getId()));

    }

    @Test
    void delete_ThrowsException_NotFoundID() {
        Producer producer = producers.getFirst();

        BDDMockito.when(repository.findById(producer.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.delete(producer.getId())).isInstanceOf(ResponseStatusException.class);

    }


}