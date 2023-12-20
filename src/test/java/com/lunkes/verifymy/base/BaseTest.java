package com.lunkes.verifymy.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lunkes.verifymy.client.clients.UserClient;
import com.lunkes.verifymy.domain.GetResponse;
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

    public static UserClient userClient = new UserClient();
    public List<User> usersToTest;

    @BeforeClass
    @Parameters({"env"})
    public void setup(@Optional("dev") String environment){
        baseURI = getProperty("api.uri." + environment);
        enableLoggingOfRequestAndResponseIfValidationFails();
        deletAllPreviusUsers();
    }

    private void deletAllPreviusUsers(){
       GetResponse users = userClient.getUsers().extract().body().as(GetResponse.class);
       users.getUsuarios().forEach((user)->{
           userClient.deletUser(user.get_id());
       });
       log.info(users.getQuantidade() + " previous users has been deleted");
    }

    protected void insertInitialData(String path){
        usersToTest = getJsonOfObject(path, new TypeReference<List<User>>() {});
        usersToTest.forEach((user -> {
            userClient.postUser(user.toBody()).statusCode(201);
        }));
    }

    @AfterClass
    public void tearDown(){
       deletAllPreviusUsers();
    }


}
