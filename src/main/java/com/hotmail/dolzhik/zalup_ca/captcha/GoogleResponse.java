package com.hotmail.dolzhik.zalup_ca.captcha;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class GoogleResponse {
    private boolean success;
    private float score;
    private String action;
    private Timestamp challenge_ts;
    private String hostname;
}
