package com.lunkes.verifymy.functional.user;

import com.lunkes.verifymy.base.BaseTest;
import com.lunkes.verifymy.domain.GetResponse;
import com.lunkes.verifymy.domain.User;
import lombok.extern.java.Log;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

@Log
public class DeleteUserTest extends BaseTest {

    @BeforeClass(groups = {"hooks"})
    public void setUpTest() {
        deletAllPreviusUsers();
        insertInitialData("src/test/resources/testMass/initialMass.json");
        log.info("Test Mass has been inserted");
    }

    @Test(groups = {"regression"})
    public void deleteUserThenReturnCorrectBodyResponseTest() throws IOException {

        /* Arrange */
        User user = userClient.getUsers().statusCode(200)
                .extract().as(GetResponse.class).getUsuarios().get(0);

        /* Act */
        userClient.deletUser(user.get_id())

                /* Assert */
                .statusCode(200)
                .body("message", equalTo( "Registro excluído com sucesso"));
    }

    @Test
    public void deleteNonexistentUserThenReturnAlertBodyResponseTest() throws IOException {

        /* Act */
        userClient.deletUser("testeIdNoexistent")

                /* Assert */
                .statusCode(200)
                .body("message", equalTo(  "Nenhum registro excluído"));
    }

}
