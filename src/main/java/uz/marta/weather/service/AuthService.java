package uz.marta.weather.service;


import uz.marta.weather.payload.ApiResponse;
import uz.marta.weather.payload.LoginDto;
import uz.marta.weather.payload.RegisterDto;

public interface AuthService {

    ApiResponse registerUser(RegisterDto registerDto);

    String loginUser(LoginDto loginDto);
}
