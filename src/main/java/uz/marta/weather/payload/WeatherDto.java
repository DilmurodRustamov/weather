package uz.marta.weather.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.marta.weather.entity.City;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {

    @NotBlank
    private Date date;

    @NotBlank
    private Float day;

    @NotBlank
    private Float min;

    @NotBlank
    private Float max;

    @NotBlank
    private Float night;

    @NotBlank
    private Float evening;

    @NotBlank
    private Float morning;

    @NotBlank
    private Double pressure;

    @NotBlank
    private Integer humidity;

    @NotBlank
    private Long cityId;
}
