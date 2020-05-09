package com.hotmail.dolzhik.zalup_ca.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConfigurationProperties(prefix = "google.recaptcha.key")
@Data
public class CaptchaConfig {
    private String site;
    private String secret;
    private float threshold;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
