
package uz.marta.weather.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private Date date;

    private Float day;

    private Float min;

    private Float max;

    private Float night;

    private Float evening;

    private Float morning;

    private Double pressure;

    private Integer humidity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private City city;

}
