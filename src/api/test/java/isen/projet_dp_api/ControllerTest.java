package isen.projet_dp_api;

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
    public void setup(){

        spec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + jsonJWT)
                .setContentType(ContentType.JSON)
                .addFilter(new ResponseLoggingFilter())
                .build();

        RestAssured.port = port;
    }

    protected <T> T put(String path, Object body, Class<T> responseClass) {

        var response = given(spec)
                .body(body)
                .put(path)
                .then()
                .extract()
                .response();

        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return response.as(responseClass);
        } else {
            throw new RuntimeException("""
                Request failed with status code: %d
                Response body: %s
                """.formatted(response.statusCode(), response.getBody().asString()));
        } //TODO know why i have 302 response instead of 400
    }
}
