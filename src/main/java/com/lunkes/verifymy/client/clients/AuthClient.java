package com.lunkes.verifymy.client.clients;

import com.lunkes.verifymy.client.base.BaseClient;
import com.lunkes.verifymy.domain.Login;
import com.lunkes.verifymy.domain.PostAuthResponse;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class AuthClient extends BaseClient {

    private static final String USER_URL = "/login";

    public ValidatableResponse postLogin(String user, String password) {
        return given()
                .spec(getSpec())
                .body(Login.builder().email(user).password(password).build())
                .post(USER_URL)
                .then();
    }

    public String getAuth(String user, String password) {
        return postLogin(user, password).statusCode(200).extract()
                .as(PostAuthResponse.class).getAuthorization();
    }

}
