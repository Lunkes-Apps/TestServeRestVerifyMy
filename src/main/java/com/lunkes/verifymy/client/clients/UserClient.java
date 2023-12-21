package com.lunkes.verifymy.client.clients;

import com.lunkes.verifymy.client.base.BaseClient;
import com.lunkes.verifymy.domain.GetResponse;
import com.lunkes.verifymy.domain.GetResponseProdutos;
import com.lunkes.verifymy.domain.Product;
import com.lunkes.verifymy.domain.User;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient {

    private static final String USER_URL = "/usuarios";

    public ValidatableResponse getUsers() {
        return given()
                .spec(getSpec())
                .get(USER_URL)
                .then();
    }

    public ValidatableResponse getUsers(HashMap filter){
        RequestSpecification spec = given()
                .spec(getSpec());

        if(filter.containsKey("nome")) spec.queryParam("nome", filter.get("nome"));
        if(filter.containsKey("email")) spec.queryParam("email", filter.get("email"));
        if(filter.containsKey("password")) spec.queryParam("password", filter.get("password"));
        if(filter.containsKey("administrador")) spec.queryParam("administrador", filter.get("administrador"));
        if(filter.containsKey("_id")) spec.queryParam("_id", filter.get("_id"));

        return spec.get(USER_URL)
                .then();
    }

    public ValidatableResponse getUserById(String id) {
        return given()
                .spec(getSpec())
                .pathParam("id", id)
                .get(USER_URL + "/{id}")
                .then();
    }

    public ValidatableResponse postUser(String userBody) {
        return given()
                .spec(getSpec())
                .body(userBody)
                .post(USER_URL)
                .then();
    }

    public ValidatableResponse deletUser(String id) {
        return given()
                .spec(getSpec())
                .pathParam("id",id)
                .delete(USER_URL + "/{id}")
                .then();
    }

    public ValidatableResponse putUser(String id, String userBody){

        return given()
                .spec(getSpec())
                .pathParam("id",id)
                .body(userBody)
                .put(USER_URL + "/{id}")
                .then();
    }

    public List<User> getUsersList(){
        return getUsers().statusCode(200)
                .extract().as(GetResponse.class).getUsuarios();
    }


}
