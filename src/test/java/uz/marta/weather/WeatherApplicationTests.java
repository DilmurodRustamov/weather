package uz.marta.weather;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class WeatherApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void getDate(){
        Date date = new Date();
        System.out.println("DATE: "+date);
    }
}
