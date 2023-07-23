package com.danir.SensorsApp.services;

import com.danir.SensorsApp.models.Weather;
import com.danir.SensorsApp.repositories.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final SensorService sensorService;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository, SensorService sensorService) {
        this.weatherRepository = weatherRepository;
        this.sensorService = sensorService;
    }

    public List<Weather> findAll() {
        return weatherRepository.findAll();
    }

    public Weather findOne(int id) {
        Optional<Weather> foundWeather = weatherRepository.findById(id);
        return foundWeather.orElse(null);
    }

    public Integer countRainyDays(){
        return weatherRepository.countRainyDays();
    }

    @Transactional
    public void addWeather(Weather measurement){
        enrichMeasurement(measurement);
        weatherRepository.save(measurement);
    }

    public void enrichMeasurement(Weather measurement){
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
        measurement.setDateTime(LocalDateTime.now());
    }

}
