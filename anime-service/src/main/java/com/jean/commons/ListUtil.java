package com.jean.commons;

import com.jean.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ListUtil {

    public List<Producer> getListProducer() {
        var datetime = "2025-01-23T09:05:55.5063595";
        var formater = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
        LocalDateTime dateTime = LocalDateTime.parse(datetime, formater);

        var prod1 = Producer.builder().id(1L).name("Teste 1").createdAt(dateTime).build();
        var prod2 = Producer.builder().id(2L).name("Teste 2").createdAt(dateTime).build();
        var prod3 = Producer.builder().id(3L).name("Teste 3").createdAt(dateTime).build();
        return new ArrayList<>(List.of(prod1, prod2, prod3));
    }
}
