package com.jean.request;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@EqualsAndHashCode
public class AnimePutRequest {
    private Long id;
    private String name;

}
