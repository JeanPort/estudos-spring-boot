package com.jean.repository;

import com.jean.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProducerHardCodeRepositoryTest {

    @InjectMocks
    private ProducerHardCodeRepository repository;

    @Mock
    private ProducerData producerData;

    private List<Producer> producerList;

    @BeforeEach
    void init() {
        var prod1 = Producer.builder().id(1L).name("Teste 1").createdAt(LocalDateTime.now()).build();
        var prod2 = Producer.builder().id(2L).name("Teste 2").createdAt(LocalDateTime.now()).build();
        var prod3 = Producer.builder().id(3L).name("Teste 3").createdAt(LocalDateTime.now()).build();

        producerList = new ArrayList<>(List.of(prod1, prod2, prod3));
    }

    @Test
    void findAll_ReturnAllProducer_WhenSuccessful(){

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var res = repository.findAll();
        Assertions.assertThat(res).isNotNull().hasSize(res.size());
    }

    @Test
    void findById_ReturnProducerById_WhenSuccessful(){

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var expected = producerList.getFirst();
        var res = repository.findById(expected.getId());
        Assertions.assertThat(res).isPresent().contains(expected);
    }

    @Test
    void findByName_ReturnProducerByName_WhenSuccessful(){

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var expected = producerList.getFirst();
        var res = repository.findByName(expected.getName());
        Assertions.assertThat(res).contains(expected);
    }

    @Test
    void findByName_ReturnEmptyList_WhenNameIsNull(){

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var res = repository.findByName(null);
        Assertions.assertThat(res).isNotNull().isEmpty();
    }

    @Test
    void createProducerWhenSuccessusful(){

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var producer = Producer.builder().id(10L).name("Novo produtor").createdAt(LocalDateTime.now()).build();

        var res = repository.save(producer);
        Assertions.assertThat(res).isEqualTo(producer).hasNoNullFieldsOrProperties();

        var producerSaveOptional = repository.findById(producer.getId());
        Assertions.assertThat(producerSaveOptional).isPresent().contains(producer);
    }

    @Test
    void delete_RemoveProducer_WhenSuccessful(){

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var remove = producerList.getFirst();
        repository.delete(remove);
        Assertions.assertThat(this.producerList).doesNotContain(remove);
    }

    @Test
    void update_UpdateProducer_WhenSuccessusful(){

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var update = producerList.getFirst();
        update.setName("update");
        repository.update(update);
        Assertions.assertThat(this.producerList).contains(update);

        var producerUpdateOptional = repository.findById(update.getId());
        Assertions.assertThat(producerUpdateOptional).isPresent();
        Assertions.assertThat(producerUpdateOptional.get().getName()).isEqualTo(update.getName());
    }
}