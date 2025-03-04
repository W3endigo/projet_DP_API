package isen.projet_dp_api.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import isen.projet_dp_api.enums.Status;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.ProjectCreationRequestResponse;
import isen.projet_dp_api.model.dto.ProjectCompaniesDTO;
import isen.projet_dp_api.model.dto.ProjectDTO;
import isen.projet_dp_api.utils.ApiResponseMessage;
import isen.projet_dp_api.utils.TestStrings;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

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

        var companies = new ArrayList<ProjectCompaniesDTO>();
        companies.add(new ProjectCompaniesDTO(TestStrings.COMPANY_SIXTH));
        var updateProjectDTO2 = new ProjectDTO(TestStrings.EMAIL_HAROLD, TestStrings.NEW_DESCRIPTION, TestStrings.TITLE, Status.TERMINE, TestStrings.START_DATE, TestStrings.END_DATE, null, companies);
        updateProjectDTO2.setCompagnies(companies);
        var newProject = put(
                path,
                updateProjectDTO2,
                ProjectDTO.class,
                jwt
        );

        assertThat(newProject.getStatus()).isEqualTo(Status.TERMINE);

        companies.add(new ProjectCompaniesDTO(TestStrings.COMPANY_FOURTH));
        updateProjectDTO2.setCompagnies(companies);
        var newProject2 = put(
                path,
                updateProjectDTO2,
                ApiException.ErrorResponse.class,
                jwt
        );

        assertThat(newProject2)
                .extracting(ApiException.ErrorResponse::message)
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

    }

    @Test
    void deleteProjectTest() {
        var path = "/api/deleteProject?title=" + TestStrings.TITLE;
        var jwt = loginAndGetToken(TestStrings.EMAIL_HAROLD, TestStrings.PASSWORD);

        var deleteProjectDTO = new ProjectDTO(TestStrings.EMAIL_HAROLD, TestStrings.DESCRIPTION, TestStrings.TITLE, Status.TERMINE, TestStrings.START_DATE, TestStrings.END_DATE, null, null);

        var project = delete(path, deleteProjectDTO, jwt);

        assertThat(project.getStatusCode()).isEqualTo(204);

        var jwt2 = loginAndGetToken(TestStrings.EMAIL_ASTRID, TestStrings.PASSWORD);

        var newDeleteProjectDTO = new ProjectDTO(TestStrings.EMAIL_KROKMOU, TestStrings.DESCRIPTION, TestStrings.DESCRIPTION, Status.TERMINE, TestStrings.START_DATE, TestStrings.END_DATE, null, null);

        var path3 = "/api/deleteProject?title=" + TestStrings.DESCRIPTION;

        assertThat(delete(path3, newDeleteProjectDTO, jwt2).getStatusCode()).isEqualTo(500);

        var path2 = "/api/deleteProject?title=" + TestStrings.TITLE_SECOND;

        var newDeleteProjectDTO2 = new ProjectDTO(TestStrings.EMAIL_ASTRID, TestStrings.DESCRIPTION, TestStrings.DESCRIPTION, Status.TERMINE, TestStrings.START_DATE, TestStrings.END_DATE, null, null);

        assertThat(delete(path2, newDeleteProjectDTO2, jwt2).getStatusCode()).isEqualTo(500);


    }


}
