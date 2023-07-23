package com.danir.SensorsApp.controllers;

import com.danir.SensorsApp.dto.WeatherDTO;
import com.danir.SensorsApp.models.Weather;
import com.danir.SensorsApp.services.WeatherService;
import com.danir.SensorsApp.util.NoSensorException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class WeatherController {
    private final ModelMapper modelMapper;

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(ModelMapper modelMapper, WeatherService weatherService) {
        this.modelMapper = modelMapper;
        this.weatherService = weatherService;
    }

    @GetMapping
    private List<WeatherDTO> getAllMeasurements(){
       return weatherService.findAll().stream().map(this::convertToWeatherDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    private Integer countRainyDays(){
        return weatherService.countRainyDays();
    }

    @PostMapping("/add")
    private ResponseEntity<HttpStatus> create(@RequestBody @Valid WeatherDTO weatherDTO,
                                              BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new NoSensorException(errorMsg.toString());
        }

        weatherService.addWeather(convertToWeather(weatherDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private WeatherDTO convertToWeatherDTO(Weather weather){
        return modelMapper.map(weather, WeatherDTO.class);
    }

    private Weather convertToWeather(WeatherDTO weatherDTO){
        return modelMapper.map(weatherDTO, Weather.class);
    }
}
