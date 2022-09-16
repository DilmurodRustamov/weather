package uz.marta.weather.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.marta.weather.entity.User;
import uz.marta.weather.payload.ApiResponse;
import uz.marta.weather.payload.LoginDto;
import uz.marta.weather.payload.RegisterDto;
import uz.marta.weather.ref.UserRole;
import uz.marta.weather.repository.UserRepository;
import uz.marta.weather.security.JwtProvider;
import uz.marta.weather.security.SecurityUser;
import uz.marta.weather.service.AuthService;

import static uz.marta.weather.apiResponsdeMessages.ResponseMessageKeys.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Override
    public ApiResponse registerUser(RegisterDto registerDto) {
        if (!registerDto.getPassword().equals(registerDto.getPerPassword()))
            return new ApiResponse(PASSWORD_NOT_EQUALS, false);
        boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(registerDto.getPhoneNumber());
        if (existsByPhoneNumber)
            return new ApiResponse(USER_ALREADY_EXISTS, false);
        User user = new User(
                registerDto.getName(),
                registerDto.getPhoneNumber(),
                passwordEncoder.encode(registerDto.getPassword()),
                UserRole.USER
        );
        userRepository.save(user);
        return new ApiResponse(USER_SAVED, true);
    }

    @Override
    public String loginUser(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getPhoneNumber(), loginDto.getPassword()));
        SecurityUser user = (SecurityUser) authenticate.getPrincipal();
        return JwtProvider.generateToken(user.getPhoneNumber(), user.getUserRole());
    }
}
