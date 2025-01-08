package com.etf.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ApplicationProperties {

    @Value("${application.lower-limit}")
    private String minValue;

    @Value("${application.upper-limit}")
    private String maxValue;

    @PostConstruct
    public void init() {
        System.setProperty("application.lower-limit", minValue);
        System.setProperty("application.upper-limit", maxValue);
    }
}
