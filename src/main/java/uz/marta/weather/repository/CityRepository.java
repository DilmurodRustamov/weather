package uz.marta.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.marta.weather.entity.City;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findAllByOrderByNameUz();

    boolean existsByNameEnOrNameUzOrNameRu(String nameEn, String nameUz, String nameRu);

}
