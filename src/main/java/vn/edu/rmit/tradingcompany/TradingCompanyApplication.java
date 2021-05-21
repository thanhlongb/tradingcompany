package vn.edu.rmit.tradingcompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class TradingCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingCompanyApplication.class, args);
	}

}
