package com.danir.SensorsApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "weather")
@Data
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "value")
    @NotNull(message = "Value cannot be empty")
    @DecimalMin(value = "-100.0", inclusive = true, message = "Value cannot be less than -100")
    @DecimalMax(value = "100.0", inclusive = true, message = "Value cannot be greater than 100")
    private Double value;

    @Column(name = "raining")
    @NotNull(message = "need information if it is raining")
    private Boolean isRaining;

    @ManyToOne
    @JoinColumn(name = "fk_sensor", referencedColumnName = "name")
    @NotNull
    private Sensor sensor;

    @Column(name = "datetime")
    private LocalDateTime dateTime;
}
