package com.lunkes.verifymy.functional.user;

import com.lunkes.verifymy.base.BaseTest;
import com.lunkes.verifymy.domain.GetResponse;
import com.lunkes.verifymy.domain.User;
import lombok.extern.java.Log;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.Matchers.*;

@Log
public class PostUserTest extends BaseTest {

    @BeforeClass(groups = {"hooks"})
    public void setUpTest() {
        deletAllPreviusUsers();
        insertInitialData("src/test/resources/testMass/initialMass.json");
        log.info("Test Mass has been inserted");
    }

    @Test(groups = {"regression"})
    public void postUserThenReturnCorrectBodyResponseTest() throws IOException {

        /* Arrange */
        User user = User.builder()
                .nome("João Carvalho QA Automation")
                .email("joaow@carvalho.com")
                .administrador("true")
                .password("vamostestar")
                .build();

        /* Act */
        userClient.postUser(user.toString())

                /* Assert */
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"),
                        "_id", matchesPattern("\\S{16}")
                );
    }

    @Test(groups = {"regression"})
    public void postExistentUserThenReturnAlertBodyResponseTest() throws IOException {

        /* Arrange */
        User user = usersToTest.get(0);
        user.setNome("Outro Nome");

        /* Act */
        userClient.postUser(user.toString())

                /* Assert */
                .statusCode(400)
                .body("message", equalTo("Este email já está sendo usado"));
    }


    @Test
    public void postUserUsingWrongFieldsThenReturnAlertTest() {

        /* Arrange */
        User user = User.builder()
                .nome("")
                .email("")
                .administrador("false")
                .password("")
                ._id("testeId")
                .build();
        /* Act */
        userClient.postUser(user.toString())

                /* Assert */
                .statusCode(400)
                .body("_id", equalTo("_id não é permitido"),
                        "email", equalTo("email não pode ficar em branco"),
                        "password", equalTo("password não pode ficar em branco"),
                        "nome", equalTo("nome não pode ficar em branco")
                );
    }


}
