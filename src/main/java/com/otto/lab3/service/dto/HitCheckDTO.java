package com.otto.lab3.service.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class HitCheckDTO {
    private Long id;
    private String sessionId;
    private Double x;
    private Double y;
    private Double r;
    private OffsetDateTime callingDate;
    private Long executionTime;
    private boolean hitStatus;
}
