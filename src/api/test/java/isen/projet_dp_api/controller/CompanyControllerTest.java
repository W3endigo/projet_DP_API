package isen.projet_dp_api.controller;

import isen.projet_dp_api.model.dto.CompanyDTO;
import isen.projet_dp_api.utils.TestStrings;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

}
