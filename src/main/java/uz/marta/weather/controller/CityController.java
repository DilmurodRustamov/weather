package uz.marta.weather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.marta.weather.entity.City;
import uz.marta.weather.payload.ApiResponse;
import uz.marta.weather.payload.CityDto;
import uz.marta.weather.payload.WeatherDto;
import uz.marta.weather.service.CityService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/city")
public class CityController {

    private final CityService cityService;

    @PreAuthorize(value = "hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping
    public HttpEntity<?> addCity(@RequestBody CityDto cityDto) {
        ApiResponse apiResponse = cityService.addCity(cityDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public HttpEntity<?> getAllCity() {
        List<City> allCities = cityService.getAllCities();
        return ResponseEntity.ok(allCities);
    }

    @PreAuthorize(value = "hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping("/{id}")
    public HttpEntity<?> getCity(@PathVariable Long id) {
        City city = cityService.getCity(id);
        return ResponseEntity.ok(city);
    }

    @PreAuthorize(value = "hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PutMapping("/{id}")
    public HttpEntity<?> editCity(@PathVariable Long id, @RequestBody CityDto cityDto) {
        ApiResponse apiResponse = cityService.updateCity(id, cityDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PutMapping("/weather/{id}")
    public HttpEntity<?> editCityWeather(@PathVariable Long id, @RequestBody WeatherDto weatherDto) {
        ApiResponse apiResponse = cityService.updateCityWeather(id, weatherDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCity(@PathVariable Long id) {
        ApiResponse apiResponse = cityService.deleteCity(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }


}
