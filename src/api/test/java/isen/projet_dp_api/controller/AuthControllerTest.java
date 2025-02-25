package isen.projet_dp_api.controller;

import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.RegisterUserRequestResponse;
import isen.projet_dp_api.model.dto.LoginDTO;
import isen.projet_dp_api.model.dto.RegisterDTO;
import isen.projet_dp_api.utils.ApiResponseMessage;
import isen.projet_dp_api.utils.TestStrings;
import isen.projet_dp_api.utils.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest extends ControllerTest {

    @Test
    void registerUserTest() {

        var path = "/api/auth/register";

        //Test successful registration
        assertThat(
                post(
                        path,
                        new RegisterDTO(TestStrings.EMAIL_HAROLD, TestStrings.PASSWORD, TestStrings.FIRST_NAME, TestStrings.LAST_NAME, TestStrings.COMPANY),
                        RegisterUserRequestResponse.class,
                        null)
                )
                .extracting(RegisterUserRequestResponse::status)
                .isEqualTo(ApiResponseMessage.SUCCESS);

        // Test successful registration but failed mail sending
        assertThat(
                post(
                        path,
                        new RegisterDTO(TestStrings.EMAIL_ASTRID, TestStrings.PASSWORD, TestStrings.FIRST_NAME, TestStrings.LAST_NAME, TestStrings.COMPANY),
                        RegisterUserRequestResponse.class,
                        null)
                )
                .extracting(RegisterUserRequestResponse::status)
                .isEqualTo(ApiResponseMessage.PARTIAL_SUCCESS);


        //Test failed registration
        assertThat(
                post(
                    path,
                    new RegisterDTO(TestStrings.EMAIL_KROKMOU, TestStrings.PASSWORD, TestStrings.FIRST_NAME, TestStrings.LAST_NAME, TestStrings.COMPANY),
                    ApiException.ErrorResponse.class,
                        null)
                )
                .extracting(ApiException.ErrorResponse::message)
                .isEqualTo(ErrorMessage.ERROR_REGISTERING_USER);


    }

    @Test
    void loginUserTest() {
        var path = "/api/auth/login";

        //Test successful login
        var token = put(
                path,
                new LoginDTO(TestStrings.EMAIL_HAROLD, TestStrings.PASSWORD),
                Map.class,
                null
        );

        assertThat(token.get("token")).isNotNull();

        //Test failed login
        assertThat(
                put(
                        path,
                        new LoginDTO(TestStrings.EMAIL_KROKMOU, TestStrings.PASSWORD),
                        ApiException.ErrorResponse.class,
                        null)
                )
                .extracting(ApiException.ErrorResponse::message)
                .isEqualTo(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }
}
