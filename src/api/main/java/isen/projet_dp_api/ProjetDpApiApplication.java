package isen.projet_dp_api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@SpringBootApplication
@RestController
public class ProjetDpApiApplication {

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/api/hello")
    public String home() {
        return "Hello Docker World";
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjetDpApiApplication.class, args);
    }

}
