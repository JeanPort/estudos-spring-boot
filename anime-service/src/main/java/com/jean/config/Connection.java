package com.jean.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Connection {

    private String url;
    private String username;
    private String password;
}
