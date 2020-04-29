package com.hotmail.dolzhik.zalup_ca.entities;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class AverageRating {
    private Double score;
    @Enumerated(EnumType.STRING)
    private RateCategory category;
}
