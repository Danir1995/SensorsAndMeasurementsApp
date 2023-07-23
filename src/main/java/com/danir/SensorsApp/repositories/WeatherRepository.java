package com.danir.SensorsApp.repositories;

import com.danir.SensorsApp.models.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {
    @Query("SELECT COUNT(distinct w.dateTime) FROM Weather w WHERE w.isRaining = true")
    Integer countRainyDays();

}
