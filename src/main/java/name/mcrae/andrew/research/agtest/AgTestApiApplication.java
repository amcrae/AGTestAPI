package name.mcrae.andrew.research.agtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableAutoConfiguration
public class AgTestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgTestApiApplication.class, args);
	}
}
