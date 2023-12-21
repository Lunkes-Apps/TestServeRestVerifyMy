package com.lunkes.verifymy.integration;

import com.lunkes.verifymy.base.BaseTest;
import com.lunkes.verifymy.domain.Product;
import com.lunkes.verifymy.domain.User;
import com.lunkes.verifymy.domain.shopcar.ItemShopCar;
import com.lunkes.verifymy.domain.shopcar.PostShopCar;
import lombok.extern.java.Log;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@Log
public class FinalizePurchaseTest extends BaseTest {

    @BeforeClass
    public void setUpTest() {
        deletAllPreviusUsers();
        insertInitialData("src/test/resources/testMass/initialMassUsers.json");
        authUser();
        deleteAllPreviusShopCars();
        deleteAllPreviusProducts();
        insertInitialProducts("src/test/resources/testMass/initialMassProdutos.json");
        log.info("Test Mass has been inserted");
    }

    @Test
    public void WhenUserPutProductsIntoShopCarAndFinalizePurchaseThanItIsDeleted() {
        /**
         * Given user and products have been created
         * When user put products into car
         * And Finalize Purchase
         * Than it is deleted
         */

        /* Arrange */
        List<Product> productsList = produtClient.getProductsList();
        List<User> usersList = userClient.getUsersList();
        User userToTest = usersList.get(0);

        auth = authClient.getAuth(userToTest.getEmail(), userToTest.getPassword());

        List<ItemShopCar> items = new ArrayList<>();
        items.add(ItemShopCar.builder()
                .idProduto(productsList.get(0).get_id())
                .quantidade(2)
                .build());

        items.add(ItemShopCar.builder()
                .idProduto(productsList.get(1).get_id())
                .quantidade(4)
                .build());

        PostShopCar postShopCar = PostShopCar.builder()
                .produtos(items).build();

        /* Act */
        shopCarClient.postShopCar(postShopCar.toString(), auth)
                .statusCode(201);

        HashMap filter = new HashMap();
        filter.put("idUsuario", userToTest.get_id());
        shopCarClient.getShopCars(filter)
                .statusCode(200)
                .body("quantidade", equalTo(1));

        shopCarClient.finalizePurchase(auth)
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"));


        /* Assert */
        shopCarClient.getShopCars(filter)
                .statusCode(200)
                .body("quantidade", equalTo(0));

    }

}
