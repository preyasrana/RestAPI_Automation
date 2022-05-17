package com.restassured.example.test;

import com.github.javafaker.Faker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.restassured.example.Constants.GET_USER_ENDPOINT;
import static com.restassured.example.Constants.USER_ID_PATH_PARAM_NAME;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class GetUserTest extends BaseTest {

    private int userId;
    private String firstName, lastName;

    @BeforeMethod
    public void before() {
        Faker faker = new Faker();
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        userId = createNewUserAndReturnUserId(firstName, lastName);
    }

    @Test(description = "Verify that a user can be retrieved")
    public void testUserRetrieval() {
        given()
                .request()
                .spec(requestSpec)
                .pathParam(USER_ID_PATH_PARAM_NAME, userId).log().all()
                .get(GET_USER_ENDPOINT)
                .then().log().all()
                .statusCode(SC_OK)
                .body("data.name", equalTo(firstName + " " + lastName));
        
        System.out.println("name of user"+(firstName + " " + lastName));
    }
}
