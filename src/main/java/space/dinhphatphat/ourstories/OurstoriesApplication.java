package space.dinhphatphat.ourstories;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("space.dinhphatphat.model")
@EnableJpaRepositories("space.dinhphatphat.repository")
@SpringBootApplication(scanBasePackages = {"space.dinhphatphat"})
public class OurstoriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(OurstoriesApplication.class, args);
	}

}
