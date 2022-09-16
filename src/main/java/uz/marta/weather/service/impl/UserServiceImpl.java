package uz.marta.weather.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.marta.weather.entity.City;
import uz.marta.weather.entity.User;
import uz.marta.weather.payload.AdminDto;
import uz.marta.weather.payload.ApiResponse;
import uz.marta.weather.repository.CityRepository;
import uz.marta.weather.repository.UserRepository;
import uz.marta.weather.security.SecurityUser;
import uz.marta.weather.service.UserService;

import javax.servlet.http.PushBuilder;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static uz.marta.weather.apiResponsdeMessages.ResponseMessageKeys.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CityRepository cityRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public ApiResponse createAmin(AdminDto adminUserDto) {
        return createUser(adminUserDto.mapToUser(passwordEncoder));
    }

    @Override
    public User getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public ApiResponse updateUser(Long id, User userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            return new ApiResponse(USER_NOT_FOUND, false);
        User user = optionalUser.get();
        user.setName(userDto.getName());
        user.setCities(userDto.getCities());
        userRepository.save(user);
        return new ApiResponse(USER_UPDATED, true);
    }

    @Override
    public ApiResponse subscribeToCity(Long cityId) {
        Optional<City> optionalCity = cityRepository.findById(cityId);
        if (optionalCity.isEmpty())
            return new ApiResponse(CITY_NOT_FOUND, false);

        City city = optionalCity.get();
        User currentUser = getCurrentUser();
        Set<City> cities = currentUser.getCities();
        cities.add(city);
        currentUser.setCities(cities);
        userRepository.save(currentUser);
        return new ApiResponse(CITY_SUCCESSFULLY_ADDED, true);
    }

    @Override
    public Set<City> getSubscriptions() {
        User user = getCurrentUser();
        return user.getCities();
    }


    private ApiResponse createUser(User user) {
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber()))
            return new ApiResponse(USER_ALREADY_EXISTS, false);
        userRepository.save(user);
        return new ApiResponse(USER_SAVED, false);
    }

    private User getCurrentUser() {
        Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authenticate.getPrincipal();
        Optional<User> optionalUser = userRepository.findById(securityUser.getUserId());
        if (optionalUser.isEmpty())
            return null;
        return optionalUser.get();
    }
}
