package com.lunkes.verifymy.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lunkes.verifymy.client.clients.AuthClient;
import com.lunkes.verifymy.client.clients.ProdutClient;
import com.lunkes.verifymy.client.clients.UserClient;
import com.lunkes.verifymy.domain.GetResponse;
import com.lunkes.verifymy.domain.GetResponseProdutos;
import com.lunkes.verifymy.domain.Product;
import com.lunkes.verifymy.domain.User;
import lombok.extern.java.Log;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.List;

import static com.lunkes.verifymy.utils.UtilsResources.getJsonOfObject;
import static com.lunkes.verifymy.utils.UtilsResources.getProperty;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;

@Log
public class BaseTest {

    public static final String PATH_CONTRACT_SCHEMAS = "src/test/resources/schemas";

    public static UserClient userClient = new UserClient();
    public static ProdutClient produtClient = new ProdutClient();
    public static AuthClient authClient = new AuthClient();

    public List<User> usersToTest;
    public List<Product> productsToTest;

    public static String auth;

    @BeforeClass
    @Parameters({"env"})
    public void setup(@Optional("local") String environment){
        baseURI = getProperty("api.uri." + environment);
        enableLoggingOfRequestAndResponseIfValidationFails();

    }

    protected void deletAllPreviusUsers(){
       GetResponse users = userClient.getUsers().extract().body().as(GetResponse.class);
       users.getUsuarios().forEach((user)->{
           userClient.deletUser(user.get_id());
       });
       log.info(users.getQuantidade() + " previous users has been deleted");
    }

    protected void deleteAllPreviusProducts(){
        GetResponseProdutos products = produtClient.getProducts()
                .extract().as(GetResponseProdutos.class);
        products.getProdutos().forEach((product -> {
            produtClient.deletProdut(product.get_id(), auth).statusCode(200);
        }));
        log.info(products.getQuantidade() + " previous users has been deleted");
    }

    protected void insertInitialData(String path){
        usersToTest = getJsonOfObject(path, new TypeReference<List<User>>() {});
        usersToTest.forEach((user -> {
            userClient.postUser(user.toBody()).statusCode(201);
        }));
    }

    protected void insertInitialProducts(String path){
        productsToTest = getJsonOfObject(path, new TypeReference<List<Product>>() {});
        productsToTest.forEach((product -> {
            produtClient.postProduct(product.toBody(), auth).statusCode(201);
        }));
    }

    protected void authUser(){
        String user = usersToTest.get(0).getEmail();
        String password = usersToTest.get(0).getPassword();
        auth = authClient.getAuth(user, password);
    }

    @AfterClass
    public void tearDown(){
        deleteAllPreviusProducts();
        deletAllPreviusUsers();
    }


}
