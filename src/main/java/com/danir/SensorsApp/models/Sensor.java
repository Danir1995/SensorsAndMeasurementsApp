package com.danir.SensorsApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "sensor")
@Data
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column
    @NotNull(message = "value can not be empty")
    @Size(min = 2, max = 32, message = "name of sensor must be between 2 and 32")
    private String name;

}
