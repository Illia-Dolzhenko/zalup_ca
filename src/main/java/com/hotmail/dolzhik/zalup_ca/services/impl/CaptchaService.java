package com.hotmail.dolzhik.zalup_ca.services.impl;

import com.hotmail.dolzhik.zalup_ca.captcha.GoogleResponse;
import com.hotmail.dolzhik.zalup_ca.config.CaptchaConfig;
import com.hotmail.dolzhik.zalup_ca.services.ICaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class CaptchaService implements ICaptchaService {

    private final CaptchaConfig captchaConfig;
    private final String verifyUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public CaptchaService(CaptchaConfig captchaConfig, RestTemplate restTemplate) {
        this.captchaConfig = captchaConfig;
        this.restTemplate = restTemplate;
        this.verifyUrl = "https://www.google.com/recaptcha/api/siteverify?secret=" + this.captchaConfig.getSecret();
    }

    @Override
    public boolean isValid(String token, String action) {

        URI googleUri = URI.create(verifyUrl + "&response=" + token);
        GoogleResponse response = restTemplate.getForObject(googleUri, GoogleResponse.class);
        System.out.println(response);

        if (response != null && response.isSuccess()) {
            return response.getScore() > captchaConfig.getThreshold() && response.getAction().equals(action);
        }

        return false;
    }
}
