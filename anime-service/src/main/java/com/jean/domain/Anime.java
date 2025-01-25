package com.jean.domain;

import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Builder
public class Anime {

    @EqualsAndHashCode.Include
    private Long id;
    private String name;

    public Anime(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
