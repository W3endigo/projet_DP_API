package isen.projet_dp_api.controller.user;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import isen.projet_dp_api.controller.ControllerTest;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.RegisterRequestResponse;
import isen.projet_dp_api.model.dto.UserDTO;
import isen.projet_dp_api.utils.ApiResponseMessage;
import isen.projet_dp_api.utils.TestStrings;
import isen.projet_dp_api.utils.exception.ErrorMessage;
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

        //Test successful registration
        assertThat(
                put(
                    this.base + "/api/user",
                    new UserDTO(TestStrings.EMAIL_HAROLD, TestStrings.PASSWORD, TestStrings.FIRST_NAME, TestStrings.LAST_NAME, TestStrings.COMPANY),
                    RegisterRequestResponse.class,
                    false)
                )
                .extracting(RegisterRequestResponse::status)
                .isEqualTo(ApiResponseMessage.SUCCESS);

        // Test successful registration but failed mail sending
        assertThat(
                put(
                        this.base + "/api/user",
                        new UserDTO(TestStrings.EMAIL_ASTRID, TestStrings.PASSWORD, TestStrings.FIRST_NAME, TestStrings.LAST_NAME, TestStrings.COMPANY),
                        RegisterRequestResponse.class,
                        false)
        )
                .extracting(RegisterRequestResponse::status)
                .isEqualTo(ApiResponseMessage.PARTIAL_SUCCESS);


        //Test failed registration
        assertThat(put(
                this.base + "/api/user",
                new UserDTO(TestStrings.EMAIL_KROKMOU, TestStrings.PASSWORD, TestStrings.FIRST_NAME, TestStrings.LAST_NAME, TestStrings.COMPANY),
                ApiException.ErrorResponse.class,
                false))
                .extracting(ApiException.ErrorResponse::message)
                .isEqualTo(ErrorMessage.ERROR_REGISTERING_USER);


    }
}
