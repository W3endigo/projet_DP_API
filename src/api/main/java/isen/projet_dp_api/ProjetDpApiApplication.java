package isen.projet_dp_api;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Log4j2
public class ProjetDpApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetDpApiApplication.class, args);
    }

}
