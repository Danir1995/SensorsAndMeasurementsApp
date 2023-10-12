package com.danir.SensorsApp.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDTO {

    @NotNull(message = "Value cannot be empty")
    @DecimalMin(value = "-100.0", inclusive = true, message = "Value cannot be less than -100")
    @DecimalMax(value = "100.0", inclusive = true, message = "Value cannot be greater than 100")
    private Double value;

    @NotNull(message = "need information if it is raining")
    private Boolean isRaining;
    @NotNull
    private SensorDTO sensor;

}
