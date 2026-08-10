package com.example.Optomeapi.dto.request;

import lombok.Data;

@Data
public class ScheduleRequest {
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;
}
