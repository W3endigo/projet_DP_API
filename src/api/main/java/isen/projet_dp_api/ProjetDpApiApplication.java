package isen.projet_dp_api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import isen.projet_dp_api.service.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import org.thymeleaf.context.Context;

@SpringBootApplication
@RestController
@Log4j2
public class ProjetDpApiApplication {

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/hello")
    public String home() {
        return "Hello Docker World";
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjetDpApiApplication.class, args);
    }

}
