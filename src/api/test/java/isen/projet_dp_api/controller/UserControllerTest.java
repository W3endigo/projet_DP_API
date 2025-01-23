package isen.projet_dp_api.controller;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import isen.projet_dp_api.ControllerTest;
import isen.projet_dp_api.model.RegisterRequestResponse;
import isen.projet_dp_api.model.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends ControllerTest {

    @LocalServerPort
    private int port;

    private URL base;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URI("http://localhost:" + port).toURL();
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    void registerUserTest() throws MalformedURLException {
        var userDTO = new UserDTO("not_an_email", "password123", "John", "Doe", "ACME Corp");

//        assertThatThrownBy(() -> put(this.base + "/api/user", userDTO, Object.class))
//                .hasMessageContaining("400");

        var test = put(this.base + "/api/user", userDTO, Object.class);

        assertThatThrownBy(() -> put(this.base + "/api/user", userDTO, RegisterRequestResponse.class))
                .hasMessageContaining("400");

    }
}
