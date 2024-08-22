package me.gyuri.tripity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TripityApplication {
    public static void main(String[] args) {
        SpringApplication.run(TripityApplication.class, args);
    }
}
