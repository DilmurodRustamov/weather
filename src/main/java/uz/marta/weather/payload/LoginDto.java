package uz.marta.weather.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull(message = "username bo'sh bo'lmasin")
    private String phoneNumber;

    @NotNull(message = "password bo'sh bo'lmasin")
    private String password;

}
