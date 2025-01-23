package isen.projet_dp_api.controller.user;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import isen.projet_dp_api.controller.ControllerTest;
import isen.projet_dp_api.model.RegisterRequestResponse;
import isen.projet_dp_api.model.dto.UserDTO;
import isen.projet_dp_api.utils.ApiResponseMessage;
import isen.projet_dp_api.utils.TestStrings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.URL;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    void registerUserTest() {
        var userDTO = new UserDTO(TestStrings.EMAIL_GOOD, TestStrings.PASSWORD, TestStrings.FIRST_NAME, TestStrings.LAST_NAME, TestStrings.COMPANY);

        assertThat(put(this.base + "/api/user", userDTO, RegisterRequestResponse.class, false))
                .extracting(RegisterRequestResponse::status)
                .isEqualTo(ApiResponseMessage.SUCCESS);
    }
}
