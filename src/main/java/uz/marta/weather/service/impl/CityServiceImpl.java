package uz.marta.weather.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.marta.weather.entity.City;
import uz.marta.weather.entity.Weather;
import uz.marta.weather.payload.ApiResponse;
import uz.marta.weather.payload.CityDto;
import uz.marta.weather.payload.WeatherDto;
import uz.marta.weather.repository.CityRepository;
import uz.marta.weather.repository.WeatherRepository;
import uz.marta.weather.service.CityService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static uz.marta.weather.apiResponseMessages.ResponseMessageKeys.*;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    private final WeatherRepository weatherRepository;

    @Override
    public ApiResponse addCity(CityDto cityDto) {
        boolean existsByName = cityRepository.existsByNameEnOrNameUzOrNameRu(cityDto.getNameEn(), cityDto.getNameUz(), cityDto.getNameRu());
        if (existsByName)
            return new ApiResponse(CITY_ALREADY_EXISTS, false);
        City city = new City();
        city.setNameUz(cityDto.getNameUz());
        city.setNameRu(cityDto.getNameRu());
        city.setNameEn(cityDto.getNameEn());
        cityRepository.save(city);
        return new ApiResponse(CITY_SAVED, true);

    }

    public City getCity(Long id) {
        Optional<City> optionalCity = cityRepository.findById(id);
        return optionalCity.get();
    }

    public List<City> getAllCities() {
        return cityRepository.findAllByOrderByNameUz();
    }

    @Transactional
    @Override
    public ApiResponse deleteCity(Long cityId) {
        cityRepository.deleteById(cityId);
        return new ApiResponse(CITY_DELETED, true);
    }

    @Transactional
    @Override
    public ApiResponse updateCity(Long cityId, CityDto cityDto) {
        Optional<City> optionalCity = cityRepository.findById(cityId);
        if (optionalCity.isEmpty()) {
            return new ApiResponse(CITY_NOT_FOUND, true);
        }
        City city = optionalCity.get();
        city.setNameUz(cityDto.getNameUz());
        city.setNameEn(cityDto.getNameEn());
        city.setNameRu(cityDto.getNameRu());
        cityRepository.save(city);
        return new ApiResponse(CITY_UPDATED, true);
    }

    @Override
    public ApiResponse updateCityWeather(Long cityId, WeatherDto weatherDto) {
        Optional<Weather> optionalWeather = weatherRepository.findByCityIdAndDate(cityId, weatherDto.getDate());
        if (optionalWeather.isEmpty())
            return new ApiResponse(WEATHER_NOT_FOUND, false);

        Weather weather = optionalWeather.get();
        weather.setDate(weatherDto.getDate());
        weather.setDay(weatherDto.getDay());
        weather.setMin(weatherDto.getMin());
        weather.setMax(weatherDto.getMax());
        weather.setNight(weatherDto.getNight());
        weather.setEvening(weatherDto.getEvening());
        weather.setMorning(weatherDto.getMorning());
        weather.setPressure(weatherDto.getPressure());
        weather.setHumidity(weatherDto.getHumidity());
        weatherRepository.save(weather);
        return new ApiResponse(WEATHER_UPDATED, true);
    }
}
