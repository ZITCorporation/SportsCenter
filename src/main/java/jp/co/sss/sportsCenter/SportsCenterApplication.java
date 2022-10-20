package jp.co.sss.sportsCenter;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@Controller
@SpringBootApplication
public class SportsCenterApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(SportsCenterApplication.class, args);
		} catch (BeanCreationException e) {
			System.out.println("AWSのRDSデータベースを開いて、または接続を確認してください。\n");
		}
	}

}
