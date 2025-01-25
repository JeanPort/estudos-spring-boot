package com.jean.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class ProducerPostResponse {

    private Long id;
    private String name;
    private LocalDateTime createdAt;

}
