package jp.co.sss.sportsCenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@Controller
@SpringBootApplication
public class SportsCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportsCenterApplication.class, args);
	}

}
