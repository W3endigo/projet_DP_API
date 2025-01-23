package isen.projet_dp_api.controller;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

@SpringBootTest
public abstract class ControllerTest {

    public static String jsonJWT = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QG1haWwuY29tIiwiaWF0IjoxNzM3NTUyNjYzLCJleHAiOjIwOTc1NTI2NjN9.tBGCvHohV8JjRq0bzgJdgD6VjaxEpZywphd2Sq7pGZaO4RFK3V_BCN9EcslIqA_cCJSCGtVLMCkU4NgNQGew5w";

    static protected RequestSpecification spec;

    @LocalServerPort
    protected int port;

    @BeforeAll
    public static void initSpec() {
        RestAssured.reset();
    }

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    protected <T> T put(String path, Object body, Class<T> responseClass, boolean includeJwt) {
        RequestSpecBuilder specBuilder = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new ResponseLoggingFilter());

        if (includeJwt) {
            specBuilder.addHeader("Authorization", "Bearer " + jsonJWT);
        }

        spec = specBuilder.build();

        return given()
                .spec(spec)
                .body(body)
                .when()
                .put(path)
                .then()
                .extract()
                .body()
                .as(responseClass);
    }
}