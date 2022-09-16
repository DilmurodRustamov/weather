package uz.marta.weather.service;

import uz.marta.weather.entity.City;
import uz.marta.weather.payload.ApiResponse;
import uz.marta.weather.payload.CityDto;
import uz.marta.weather.payload.WeatherDto;

import java.util.List;

public interface CityService {

    ApiResponse addCity(CityDto cityDto);

    City getCity(Long id);

    List<City> getAllCities();

    ApiResponse deleteCity(Long regionId);

    ApiResponse updateCity(Long cityId, CityDto cityDto);

    ApiResponse updateCityWeather(Long cityId, WeatherDto weatherDto);
}