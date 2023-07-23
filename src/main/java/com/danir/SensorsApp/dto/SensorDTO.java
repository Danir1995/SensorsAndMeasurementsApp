package com.danir.SensorsApp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorDTO {

    @NotNull(message = "value can not be empty")
    @Size(min = 2, max = 32, message = "name of sensor must be between 2 and 32")
    private String name;

}
