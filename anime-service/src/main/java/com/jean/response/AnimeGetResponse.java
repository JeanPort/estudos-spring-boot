package com.jean.response;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class AnimeGetResponse {

    private Long id;
    private String name;


}
