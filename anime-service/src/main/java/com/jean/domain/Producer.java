package com.jean.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producer {

    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private LocalDateTime createdAt;



    public Producer(Long id, String name, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.createdAt = dateTime;
    }


}
