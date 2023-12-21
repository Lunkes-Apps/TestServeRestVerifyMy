package com.lunkes.verifymy.client.clients;

import com.lunkes.verifymy.client.base.BaseClient;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ShopCarClient extends BaseClient {

    private static final String USER_URL = "/carrinhos";


    public ValidatableResponse getShopCars() {
        return given()
                .spec(getSpec())
                .get(USER_URL)
                .then();
    }

    public ValidatableResponse getShopCars(HashMap filter){
        RequestSpecification spec = given()
                .spec(getSpec());

        if(filter.containsKey("precoTotal")) spec.queryParam("precoTotal", filter.get("precoTotal"));
        if(filter.containsKey("idUsuario")) spec.queryParam("idUsuario", filter.get("idUsuario"));
        if(filter.containsKey("quantidadeTotal")) spec.queryParam("quantidadeTotal", filter.get("quantidadeTotal"));
        if(filter.containsKey("_id")) spec.queryParam("_id", filter.get("_id"));

        return spec.get(USER_URL)
                .then();
    }

    public ValidatableResponse getShopCarById(String id) {
        return given()
                .spec(getSpec())
                .pathParam("id", id)
                .get(USER_URL + "/{id}")
                .then();
    }

    public ValidatableResponse postShopCar(String body, String auth) {

        return given()
                .spec(getSpec())
                .body(body)
                .header("Authorization", auth)
                .post(USER_URL)
                .then();
    }

    public ValidatableResponse finalizePurchase(String auth) {
        return given()
                .spec(getSpec())
                .header("Authorization", auth)
                .delete(USER_URL + "/concluir-compra")
                .then();
    }

    public ValidatableResponse putShopCar(String id, String userBody){

        return given()
                .spec(getSpec())
                .pathParam("id",id)
                .body(userBody)
                .put(USER_URL + "/{id}")
                .then();
    }

}
