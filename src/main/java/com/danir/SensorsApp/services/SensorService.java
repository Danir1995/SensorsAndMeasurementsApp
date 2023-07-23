package com.danir.SensorsApp.services;

import com.danir.SensorsApp.models.Sensor;
import com.danir.SensorsApp.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }
    public List<Sensor> findAll() {
        return sensorsRepository.findAll();
    }

    public Sensor findOne(int id) {
        Optional<Sensor> foundSensor = sensorsRepository.findById(id);
        return foundSensor.orElse(null);
    }
    @Transactional
    public void save(Sensor sensor){
        sensorsRepository.save(sensor);
    }

    public Optional<Sensor> findByName(String name) {
       return sensorsRepository.findByName(name);
    }
}
