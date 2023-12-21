package com.lunkes.verifymy.functional.product;

import com.lunkes.verifymy.base.BaseTest;
import com.lunkes.verifymy.domain.GetResponseProdutos;
import com.lunkes.verifymy.domain.Product;
import lombok.extern.java.Log;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@Log
public class GetProductTest extends BaseTest {

    @BeforeMethod
    public void setUpTest() {
        deletAllPreviusUsers();
        insertInitialData("src/test/resources/testMass/initialMassUsers.json");
        authUser();
        deleteAllPreviusProducts();
        insertInitialProducts("src/test/resources/testMass/initialMassProdutos.json");
        log.info("Test Mass has been inserted");
    }

    @Test
    public void getProductsThenReturCorrectList() {

        /* Act */
        HashMap filter = new HashMap();
        filter.put("nome", "QA Automation");
        List<Product> productList = produtClient.getProducts(filter)
                /* Assert */
                .statusCode(200)
                .body("quantidade", equalTo(3))
                .extract().as(GetResponseProdutos.class).getProdutos();

        /* Assert */
        productList.forEach((product -> {
            Product prodAssert = productsToTest.stream()
                    .filter(p -> p.getDescricao().equals(product.getDescricao()))
                    .findFirst().get();

            Assert.assertEquals(product.getNome(), prodAssert.getNome());
            Assert.assertEquals(product.getQuantidade(), prodAssert.getQuantidade());
            Assert.assertEquals(product.getPreco(), prodAssert.getPreco());
            Assert.assertNotNull(product.get_id());
        }));
    }

    @Test
    public void getProductsWithFilterThenReturCorrectList() {

        /* Arrange */
        HashMap filter = new HashMap();
        filter.put("nome", "DELL");

        /* Act */
        List<Product> productList = produtClient.getProducts(filter)
                /* Assert */
                .statusCode(200)
                .body("quantidade", equalTo(2))
                .extract().as(GetResponseProdutos.class).getProdutos();

        /* Assert */
        productList.forEach((product -> {
            Product prodAssert = productsToTest.stream()
                    .filter(p -> p.getDescricao().equals(product.getDescricao()))
                    .findFirst().get();

            Assert.assertEquals(product.getNome(), prodAssert.getNome());
            Assert.assertEquals(product.getQuantidade(), prodAssert.getQuantidade());
            Assert.assertEquals(product.getPreco(), prodAssert.getPreco());
            Assert.assertNotNull(product.get_id());
        }));
    }

    @Test
    public void getProductByIdThenReturCorrectList() {

        /* Arrange */
        Product prodAssert = produtClient.getProducts()
                .statusCode(200).extract().as(GetResponseProdutos.class)
                .getProdutos().get(0);


        /* Act */
        Product product = produtClient.getProductById(prodAssert.get_id())
                /* Assert */
                .statusCode(200)
                .extract().as(Product.class);

        /* Assert */
        Assert.assertEquals(product.getNome(), prodAssert.getNome());
        Assert.assertEquals(product.getQuantidade(), prodAssert.getQuantidade());
        Assert.assertEquals(product.getPreco(), prodAssert.getPreco());
        Assert.assertNotNull(product.get_id());

    }

    @Test
    public void getProductByNonexistentThenReturAlertMessage() {

        /* Act */
        produtClient.getProductById("asdasd")
                /* Assert */
                .statusCode(400)
                .body("message", equalTo("Produto não encontrado"));

    }
}
