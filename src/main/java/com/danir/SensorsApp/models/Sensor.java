package com.danir.SensorsApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "sensor")
@Getter
@Setter
public class Sensor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column
    @NotNull(message = "value can not be empty")
    @Size(min = 2, max = 32, message = "name of sensor must be between 2 and 32")
    private String name;

}
