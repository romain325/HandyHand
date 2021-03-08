package Api.ApiApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * API Entry Point
 */
@SpringBootApplication
public class ApiAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiAppApplication.class, args);
	}
}
