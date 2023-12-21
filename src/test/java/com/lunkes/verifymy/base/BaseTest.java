package com.lunkes.verifymy.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lunkes.verifymy.client.clients.AuthClient;
import com.lunkes.verifymy.client.clients.ProdutClient;
import com.lunkes.verifymy.client.clients.ShopCarClient;
import com.lunkes.verifymy.client.clients.UserClient;
import com.lunkes.verifymy.domain.GetResponse;
import com.lunkes.verifymy.domain.GetResponseProdutos;
import com.lunkes.verifymy.domain.Product;
import com.lunkes.verifymy.domain.User;
import com.lunkes.verifymy.domain.shopcar.GetResponseShopCar;
import lombok.extern.java.Log;
import org.testng.annotations.*;

import java.util.HashMap;
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
    public static ShopCarClient shopCarClient = new ShopCarClient();

    public List<User> usersToTest;
    public List<Product> productsToTest;

    public static String auth;

    @BeforeClass
    @Parameters({"env"})
    public void setup(@Optional("dev") String environment) {
        baseURI = getProperty("api.uri." + environment);
        enableLoggingOfRequestAndResponseIfValidationFails();

    }

    protected void deletAllPreviusUsers() {
       userClient.getUsersList().forEach((user) -> {
            userClient.deletUser(user.get_id()).statusCode(200);
            log.info("deletou " + user.getEmail());
        });
    }

    protected void deleteAllPreviusProducts() {
        produtClient.getProductsList().forEach((product -> {
            produtClient.deletProduct(product.get_id(), auth).statusCode(200);
        }));
    }

    protected void deleteAllPreviusShopCars() {
        userClient.getUsersList().forEach(user -> {
            auth = authClient.getAuth(user.getEmail(), user.getPassword());
            shopCarClient.finalizePurchase(auth)
                    .statusCode(200);
        });
    }

    protected void insertInitialData(String path) {
        usersToTest = getJsonOfObject(path, new TypeReference<List<User>>() {
        });
        usersToTest.forEach((user -> {
            userClient.postUser(user.toString()).statusCode(201);
        }));
        log.info("users have been inserted");
    }

    protected void insertInitialProducts(String path) {
        productsToTest = getJsonOfObject(path, new TypeReference<List<Product>>() {
        });
        productsToTest.forEach((product -> {
            produtClient.postProduct(product.toBody(), auth).statusCode(201);
        }));
    }

    protected void authUser() {
        String user = usersToTest.get(0).getEmail();
        String password = usersToTest.get(0).getPassword();
        auth = authClient.getAuth(user, password);
    }

    @AfterMethod
    public void tearDown() {
        deleteAllPreviusShopCars();
        deleteAllPreviusProducts();
        deletAllPreviusUsers();
    }


}
