package uz.marta.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.marta.weather.entity.Weather;

import java.util.Date;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    Optional<Weather> findByCityIdAndDate(Long city_id, Date date);
}
