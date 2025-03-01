package isen.projet_dp_api.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import isen.projet_dp_api.enums.Status;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.ProjectCreationRequestResponse;
import isen.projet_dp_api.model.UpdateUserRequestResponse;
import isen.projet_dp_api.model.dto.ProjectDTO;
import isen.projet_dp_api.utils.ApiResponseMessage;
import isen.projet_dp_api.utils.TestStrings;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerTest extends ControllerTest{

    @Test
    void createProjectTest() {

        var path = "/api/project";
        var jwt = loginAndGetToken(TestStrings.EMAIL_HAROLD, TestStrings.PASSWORD);

        assertThat(
                put(
                        path,
                        new ProjectDTO(TestStrings.EMAIL_HAROLD, TestStrings.DESCRIPTION, TestStrings.TITLE, Status.EN_COURS, TestStrings.START_DATE, TestStrings.END_DATE, null, null),
                        ProjectCreationRequestResponse.class,
                        jwt
                )
        ).extracting(ProjectCreationRequestResponse::status).isEqualTo(ApiResponseMessage.SUCCESS);

        assertThat(
                put(
                        path,
                        new ProjectDTO(TestStrings.EMAIL_HAROLD, TestStrings.DESCRIPTION, "TestStrings TITLE", Status.EN_COURS, TestStrings.START_DATE, TestStrings.END_DATE, null, null),
                        ApiException.ErrorResponse.class,
                        jwt
                )
        ).extracting(ApiException.ErrorResponse::message).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @Test
    void updateProjectTest() {

        var path = "/api/updateProject?title=" + TestStrings.TITLE;
        var jwt = loginAndGetToken(TestStrings.EMAIL_HAROLD, TestStrings.PASSWORD);

        var updateProjectDTO = new ProjectDTO(TestStrings.EMAIL_HAROLD, TestStrings.NEW_DESCRIPTION, TestStrings.TITLE, Status.TERMINE, TestStrings.START_DATE, TestStrings.END_DATE, null, null);

        var project = put(
                path,
                updateProjectDTO,
                ProjectDTO.class,
                jwt
        );

        assertThat(project.getStatus()).isEqualTo(Status.TERMINE);

    }
}
