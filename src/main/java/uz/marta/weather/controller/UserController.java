package uz.marta.weather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.marta.weather.entity.City;
import uz.marta.weather.entity.User;
import uz.marta.weather.payload.AdminDto;
import uz.marta.weather.payload.ApiResponse;
import uz.marta.weather.security.SecurityUser;
import uz.marta.weather.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/admin")
    public HttpEntity<?> createAdmin(@Valid @RequestBody AdminDto adminDto) {
        ApiResponse apiResponse = userService.createAmin(adminDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping
    public HttpEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize(value = "hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping("/{id}")
    public HttpEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public HttpEntity<?> editUser(@PathVariable Long id, User user) {
        ApiResponse apiResponse = userService.updateUser(id, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('ROLE_USER')")
    @PutMapping("/subscribe-to-city/{cityId}")
    public HttpEntity<?> subscribeToCity(@PathVariable Long cityId) {
        ApiResponse apiResponse = userService.subscribeToCity(cityId);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/get-subscriptions")
    public Set<City> getSubscriptions() {
        return userService.getSubscriptions();
    }

}
