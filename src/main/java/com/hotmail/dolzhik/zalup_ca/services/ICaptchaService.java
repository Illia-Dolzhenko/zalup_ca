package com.hotmail.dolzhik.zalup_ca.services;

public interface ICaptchaService {
    boolean isValid(String token, String action);
}
