package isen.projet_dp_api.controller;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import isen.projet_dp_api.model.dto.LoginDTO;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.net.URL;
import java.util.Map;

import static io.restassured.RestAssured.given;

@SpringBootTest
public abstract class ControllerTest {

    @LocalServerPort
    private int port;

    private URL base;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URI("http://localhost:" + port).toURL();
        RestAssured.defaultParser = Parser.JSON;
    }


    static protected RequestSpecification spec;

    protected String loginAndGetToken(String email, String password) {
        var path = "/api/auth/login";
        var response = put(
                path,
                new LoginDTO(email, password),
                Map.class,
                null
        );
        return (String) response.get("token");
    }


    protected <T> T get(String path, Class<T> responseClass, String jwt) {
        RequestSpecBuilder specBuilder = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new ResponseLoggingFilter());

        if (jwt != null) {
            specBuilder.addHeader("Authorization", "Bearer " + jwt);
        }

        spec = specBuilder.build();

        return given()
                .spec(spec)
                .when()
                .get(this.base + path)
                .then()
                .extract()
                .body()
                .as(responseClass);
    }

    protected <T> T put(String path, Object body, Class<T> responseClass, String jwt) {
        RequestSpecBuilder specBuilder = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new ResponseLoggingFilter());

        if (jwt != null) {
            specBuilder.addHeader("Authorization", "Bearer " + jwt);
        }

        spec = specBuilder.build();

        return given()
                .spec(spec)
                .body(body)
                .when()
                .put(this.base + path)
                .then()
                .extract()
                .body()
                .as(responseClass);
    }

    protected <T> T post(String path, Object body, Class<T> responseClass, String jwt) {
        RequestSpecBuilder specBuilder = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new ResponseLoggingFilter());

        if (jwt != null) {
            specBuilder.addHeader("Authorization", "Bearer " + jwt);
        }

        spec = specBuilder.build();

        return given()
                .spec(spec)
                .body(body)
                .when()
                .post(this.base + path)
                .then()
                .extract()
                .body()
                .as(responseClass);
    }
}