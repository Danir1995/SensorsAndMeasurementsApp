package com.danir.SensorsApp.services;

import com.danir.SensorsApp.dto.WeatherDTO;
import com.danir.SensorsApp.models.Weather;
import com.danir.SensorsApp.repositories.WeatherRepository;
import com.danir.SensorsApp.util.NoSensorException;
import com.danir.SensorsApp.util.SensorErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository, SensorService sensorService, ModelMapper modelMapper) {
        this.weatherRepository = weatherRepository;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    public List<WeatherDTO> getAllMeasurements() {
        return weatherRepository.findAll().stream().map(this::convertToWeatherDTO)
                .collect(Collectors.toList());
    }

    public Weather findOne(int id) {
        Optional<Weather> foundWeather = weatherRepository.findById(id);
        return foundWeather.orElse(null);
    }

    public Integer countRainyDays(){
        return weatherRepository.countRainyDays();
    }

    @Transactional
    public void addWeather(WeatherDTO measurement){
        enrichMeasurement(convertToWeather(measurement));
        weatherRepository.save(convertToWeather(measurement));
    }

    public void enrichMeasurement(Weather measurement) throws NoSensorException{
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
        measurement.setDateTime(LocalDateTime.now());
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(NoSensorException e){
        SensorErrorResponse response = new SensorErrorResponse(
                "No Sensor exists with this name",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private WeatherDTO convertToWeatherDTO(Weather weather){
        return modelMapper.map(weather, WeatherDTO.class);
    }

    private Weather convertToWeather(WeatherDTO weatherDTO){
        return modelMapper.map(weatherDTO, Weather.class);
    }


}
