package uz.marta.weather.service;

import uz.marta.weather.entity.City;
import uz.marta.weather.entity.User;
import uz.marta.weather.payload.AdminDto;
import uz.marta.weather.payload.ApiResponse;
import uz.marta.weather.security.SecurityUser;

import java.util.List;
import java.util.Set;

public interface UserService {

    ApiResponse createAmin(AdminDto adminUserDto);

    User getUser(Long id);

    List<User> getAllUsers();

    ApiResponse updateUser(Long id, User user);

    ApiResponse subscribeToCity(Long cityId);

    Set<City> getSubscriptions();
}
