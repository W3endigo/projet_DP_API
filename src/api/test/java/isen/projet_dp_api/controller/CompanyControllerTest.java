package isen.projet_dp_api.controller;

import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.dto.CompanyDTO;
import isen.projet_dp_api.utils.TestStrings;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyControllerTest extends ControllerTest {

    @Test
    void registerCompanyTest() {

        var jwt = loginAndGetToken(TestStrings.EMAIL_HAROLD, TestStrings.PASSWORD);
        var path = "/api/company";

        var companyDTO = new CompanyDTO(TestStrings.COMPANY);

        assertThat(
                put(
                        path,
                        companyDTO,
                        CompanyDTO.class,
                        jwt
                )
        ).isEqualTo(companyDTO);

    }

    @Test
    void getCompanyByNameTest() {

        var jwt = loginAndGetToken(TestStrings.EMAIL_HAROLD, TestStrings.PASSWORD);
        var path = "/api/company/" + TestStrings.COMPANY.replace(" ", "+");

        var company = get(path, CompanyDTO.class, jwt);

        assertThat(company.getName()).isEqualTo(TestStrings.COMPANY);
    }

    @Test
    void getCompanyByBadNameTest() {

        var jwt = loginAndGetToken(TestStrings.EMAIL_HAROLD, TestStrings.PASSWORD);
        var path = "/api/company/" + "Tony";

        var response = get(path, ApiException.ErrorResponse.class, jwt);

        assertThat(response)
                .extracting(ApiException.ErrorResponse::message)
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }


    @Test
    void getAllCompaniesTest() {

        var path = "/api/companies";
        var companies = get(path, List.class, null);

        for (var company : companies) {
            assertThat(company)
                    .isNotNull()
                    .hasFieldOrPropertyWithValue("name", TestStrings.COMPANY);
        }
    }

    @Test
    void deleteCompanyNameTest() {
        var path = "/api/company/" + TestStrings.COMPANY_THIRD;

        var jwt = loginAndGetToken(TestStrings.EMAIL_HAROLD, TestStrings.PASSWORD);

        assertThat(delete(path, new CompanyDTO(TestStrings.COMPANY), jwt).getStatusCode()).isEqualTo(200);

    }

}
