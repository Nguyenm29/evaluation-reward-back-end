package com.vis.backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeKeepingResponse {
    private String employeeId;
    private String name;
    private float[] workDays;
    private int overTime;
}
