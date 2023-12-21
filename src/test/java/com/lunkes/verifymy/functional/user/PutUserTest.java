package com.lunkes.verifymy.functional.user;

import com.lunkes.verifymy.base.BaseTest;
import com.lunkes.verifymy.domain.GetResponse;
import com.lunkes.verifymy.domain.User;
import lombok.extern.java.Log;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

@Log
public class PutUserTest extends BaseTest {

    @BeforeMethod
    public void setUpTest() {
        deletAllPreviusUsers();
        insertInitialData("src/test/resources/testMass/initialMass.json");
        log.info("Test Mass has been inserted");
    }

    @Test
    public void putUserThenReturnCorrectBodyResponseTest() throws IOException {

        /* Arrange */
        User user = userClient.getUsersList().get(0);

        String id = user.get_id();
        user.set_id(null);
        user.setNome("Um novo nome teste QA Automation");
        String body = user.toString();

        /* Act */
        userClient.putUser(id, body)

                /* Assert */
                .statusCode(200)
                .body("message", equalTo("Registro alterado com sucesso"));

        /* Assert */
        userClient.getUserById(id)
                .statusCode(200)
                .body("nome", equalTo(user.getNome()),
                        "email", equalTo(user.getEmail()),
                        "password", equalTo(user.getPassword()),
                        "administrador", equalTo(String.valueOf(user.getAdministrador())),
                        "_id", equalTo(id));
    }

    @Test
    public void putNonexistentUserThenReturnAlertBodyResponseTest() throws IOException {

        /* Arrange */
        User user = userClient.getUsers().statusCode(200)
                .extract().as(GetResponse.class).getUsuarios().get(3);

        String id = "tesIdABC QA Automation";
        user.set_id(null);
        user.setNome("Um novo nome");
        user.setEmail("novaaaaao@gmail.com");
        String body = user.toString();

        /* Act */
        userClient.putUser(id, body)

                /* Assert */
                .statusCode(400)
                .body("message", equalTo("Nenhum Registro Alterado"));
    }


}
