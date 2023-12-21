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
import static org.hamcrest.Matchers.notNullValue;

@Log
public class GetUserTest extends BaseTest {

    @BeforeClass
    public void setUpTest(){
        deletAllPreviusUsers();
        insertInitialData("src/test/resources/testMass/initialMass.json");
        log.info("Test Mass has been inserted");
    }

    @Test
    public void getUsersThenReturnCorrectBodyUserListTest() throws IOException {

        /* Act */
        userClient.getUsers()

                /* Assert */
                .statusCode(200)
                .body("quantidade", equalTo(6),
                        "usuarios.find{it.nome == 'Testador Santos'}.email",
                        equalTo("testador@santos.com"),
                        "usuarios.find{it.nome == 'Testador Santos'}.password",
                        equalTo("teste1234"),
                        "usuarios.find{it.nome == 'Testador Santos'}.administrador",
                        equalTo("true"),
                        "usuarios.find{it.nome == 'Testador Santos'}._id",
                        notNullValue()
                );
    }

    @Test
    public void getUserByIdThenReturnSpecificUserTest() {

        /* Arrange */
        HashMap<String, String> filter = new HashMap<>();
        filter.put("nome", "Gabriel Pereira");
        User userTest = userClient.getUsers(filter).extract().as(GetResponse.class).getUsuarios().get(0);

        /* Act */
        userClient.getUserById(userTest.get_id())

                /* Assert */
                .statusCode(200)
                .body(
                        "nome", equalTo(userTest.getNome()),
                        "email", equalTo(userTest.getEmail()),
                        "password", equalTo(userTest.getPassword()),
                        "administrador", equalTo(String.valueOf(userTest.isAdministrador())),
                        "_id", equalTo(userTest.get_id())
                );
    }

    @Test
    public void getUserByNonexistentIdThenReturnSpecificUserTest() {

        /* Act */
        userClient.getUserById("NoExistentId123")

                /* Assert */
                .statusCode(400)
                .body(
                        "message", equalTo("Usuário não encontrado")
                );
    }

    @Test
    public void getUserByFilterThenReturnSpecifUserTest() {

        /* Arrange */
        HashMap<String, String> filter = new HashMap<>();
        filter.put("nome", "Test");

        /* Act */
        userClient.getUsers(filter)

                /* Assert */
                .statusCode(200)
                .body(
                        "quantidade", equalTo(4),
                        "usuarios.find{it.nome == 'Testador Santos'}.email", equalTo("testador@santos.com"),
                        "usuarios.find{it.nome == 'Testador Santos'}.password", equalTo("teste1234"),
                        "usuarios.find{it.nome == 'Testador Santos'}.administrador", equalTo("true"),

                        "usuarios.find{it.nome == 'Testador Da Silva'}.email", equalTo("testador@silva.com"),
                        "usuarios.find{it.nome == 'Testador Da Silva'}.password", equalTo("teste123"),
                        "usuarios.find{it.nome == 'Testador Da Silva'}.administrador", equalTo("true")
                );
    }


}
