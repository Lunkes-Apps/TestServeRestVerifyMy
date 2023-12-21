package com.lunkes.verifymy.client.clients;

import com.beust.ah.A;
import com.lunkes.verifymy.client.base.BaseClient;
import com.lunkes.verifymy.domain.GetResponseProdutos;
import com.lunkes.verifymy.domain.Product;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ProdutClient extends BaseClient {

    private static final String USER_URL = "/produtos";


    public ValidatableResponse getProducts() {
        return given()
                .spec(getSpec())
                .get(USER_URL)
                .then();
    }

    public ValidatableResponse getProducts(HashMap filter){
        RequestSpecification spec = given()
                .spec(getSpec());

        if(filter.containsKey("nome")) spec.queryParam("nome", filter.get("nome"));
        if(filter.containsKey("preco")) spec.queryParam("preco", filter.get("preco"));
        if(filter.containsKey("descricao")) spec.queryParam("descricao", filter.get("descricao"));
        if(filter.containsKey("quantidade")) spec.queryParam("quantidade", filter.get("quantidade"));
        if(filter.containsKey("_id")) spec.queryParam("_id", filter.get("_id"));

        return spec.get(USER_URL)
                .then();
    }

    public ValidatableResponse getProductById(String id) {
        return given()
                .spec(getSpec())
                .pathParam("id", id)
                .get(USER_URL + "/{id}")
                .then();
    }

    public ValidatableResponse postProduct(String body, String auth) {

        return given()
                .spec(getSpec())
                .body(body)
                .header("Authorization", auth)
                .post(USER_URL)
                .then();
    }

    public ValidatableResponse deletProduct(String id, String auth) {
        return given()
                .spec(getSpec())
                .header("Authorization", auth)
                .pathParam("id",id)
                .delete(USER_URL + "/{id}")
                .then();
    }

    public ValidatableResponse putProduct(String id, String userBody){

        return given()
                .spec(getSpec())
                .pathParam("id",id)
                .body(userBody)
                .put(USER_URL + "/{id}")
                .then();
    }

    public List<Product> getProductsList(){
        HashMap filter = new HashMap();
        filter.put("nome", "QA Automation");
        return getProducts(filter).statusCode(200)
                .extract().as(GetResponseProdutos.class).getProdutos();
    }
}
