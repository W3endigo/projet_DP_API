package isen.projet_dp_api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import isen.projet_dp_api.service.email2.JavaSmtpGmailSenderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@SpringBootApplication
@RestController
public class ProjetDpApiApplication {


    private final JavaSmtpGmailSenderService senderService;


    public ProjetDpApiApplication(JavaSmtpGmailSenderService senderService) {
        this.senderService = senderService;
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/api/hello")
    public String home() {
        return "Hello Docker World";
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjetDpApiApplication.class, args);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void sendMail(){
        senderService.sendEmail("labmanagerresearch@gmail.com","This is subject","This is email body");
    }

}
