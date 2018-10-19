package pl.edu.agh.kis.databases.confectionery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("pl.edu.agh.kis.databases.confectionery.infrastructure")
@EnableJpaRepositories("pl.edu.agh.kis.databases.confectionery.infrastructure")
public class ConfectioneryApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConfectioneryApplication.class, args);
	}
}
