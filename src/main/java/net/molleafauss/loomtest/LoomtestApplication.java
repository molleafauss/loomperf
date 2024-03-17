package net.molleafauss.loomtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LoomtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoomtestApplication.class, args);
    }

}
