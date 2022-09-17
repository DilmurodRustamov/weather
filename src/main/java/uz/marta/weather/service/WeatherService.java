package uz.marta.weather.service;

import uz.marta.weather.payload.ApiResponse;
import uz.marta.weather.payload.WeatherDto;

public interface WeatherService {

    ApiResponse addWeather(WeatherDto weatherDto);
}
