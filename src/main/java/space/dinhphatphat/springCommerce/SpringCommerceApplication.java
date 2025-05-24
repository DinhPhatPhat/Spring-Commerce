package space.dinhphatphat.springCommerce;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;

@EntityScan("space.dinhphatphat.model")
@EnableJpaRepositories("space.dinhphatphat.repository")
@SpringBootApplication(scanBasePackages = {"space.dinhphatphat"})
public class SpringCommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCommerceApplication.class, args);
	}
	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
	}
}
