package isen.projet_dp_api.controller;

import isen.projet_dp_api.model.UpdateUserRequestResponse;
import isen.projet_dp_api.model.dto.UserDTO;
import isen.projet_dp_api.utils.TestStrings;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends ControllerTest {

    @Test
    void getUserDetailsTest() {

        var jwt = loginAndGetToken(TestStrings.EMAIL_HAROLD, TestStrings.PASSWORD);
        var path = "/api/user";

        var user = get(path, UserDTO.class, jwt);

        assertThat(user.getPassword()).isNull();
        assertThat(user.getFirstName()).isEqualTo(TestStrings.FIRST_NAME);
    }

    @Test
    void updateUserDetailsTest() {

        var jwt = loginAndGetToken(TestStrings.EMAIL_HAROLD, TestStrings.PASSWORD);
        var path = "/api/user";

        var updateUserDTO = new UserDTO(TestStrings.PASSWORD_SECONDARY, TestStrings.FIRST_NAME_SECONDARY, TestStrings.LAST_NAME_SECONDARY, TestStrings.COMPANY_SECONDARY);

        // Make the request with the token
        var user = put(
                path,
                updateUserDTO,
                UpdateUserRequestResponse.class,
                jwt
        ).dataUser();

        assertThat(user.getPassword()).isNull();
        assertThat(user.getFirstName()).isEqualTo(TestStrings.FIRST_NAME_SECONDARY);

    }

    @Test
    void deleterUserDetailsTest() {

        var jwt = loginAndGetToken(TestStrings.EMAIL_HAROLD, TestStrings.PASSWORD);
        var path = "/api/user";

        var userDTO = new UserDTO(TestStrings.PASSWORD, TestStrings.FIRST_NAME, TestStrings.LAST_NAME, TestStrings.COMPANY);
        var user = delete(path, userDTO, jwt);

        assertThat(user.getStatusCode()).isEqualTo(200);


        var jwt2 = loginAndGetToken(TestStrings.EMAIL_ASTRID, TestStrings.PASSWORD);

        userDTO = new UserDTO(TestStrings.PASSWORD, TestStrings.FIRST_NAME, TestStrings.LAST_NAME, TestStrings.COMPANY);
        user = delete(path, userDTO, jwt2);

        assertThat(user.getStatusCode()).isEqualTo(500);


    }

    @Test
    void getParticipationsTest() {

        var jwt = loginAndGetToken(TestStrings.EMAIL_HAROLD, TestStrings.PASSWORD);
        var path = "/api/user/participations";

        var projects = get(path, List.class, jwt);

        for (var project : projects) {
            assertThat(project)
                    .isNotNull()
                    .hasFieldOrPropertyWithValue("title", TestStrings.TITLE);
        }

    }


}
