package dev.gabrielcardoso.covidtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class CovidTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovidTrackerApplication.class, args);
	}

}
