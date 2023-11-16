package com.example.testcontainerswiremockexample;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "github")
public record GHProperties(String url, String token) {

}
