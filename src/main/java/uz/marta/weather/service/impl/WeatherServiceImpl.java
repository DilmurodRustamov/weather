package uz.marta.weather.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.marta.weather.entity.City;
import uz.marta.weather.entity.Weather;
import uz.marta.weather.payload.ApiResponse;
import uz.marta.weather.payload.WeatherDto;
import uz.marta.weather.repository.CityRepository;
import uz.marta.weather.repository.WeatherRepository;
import uz.marta.weather.service.WeatherService;

import java.util.Optional;

import static uz.marta.weather.apiResponsdeMessages.ResponseMessageKeys.CITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;

    private final CityRepository cityRepository;

    @Override
    public ApiResponse addWeather(WeatherDto weatherDto) {
        Optional<City> optionalCity = cityRepository.findById(weatherDto.getCityId());
        if (optionalCity.isEmpty())
            return new ApiResponse(CITY_NOT_FOUND,false);
        Weather weather = new Weather();
        weather.setDate(weatherDto.getDate());
        weather.setDay(weatherDto.getDay());
        weather.setMin(weatherDto.getMin());
        weather.setMax(weatherDto.getMax());
        weather.setNight(weatherDto.getNight());
        weather.setEvening(weatherDto.getEvening());
        weather.setMorning(weatherDto.getMorning());
        weather.setPressure(weatherDto.getPressure());
        weather.setHumidity(weatherDto.getHumidity());
        weather.setCity(optionalCity.get());
        weatherRepository.save(weather);
        return null;
    }
}
