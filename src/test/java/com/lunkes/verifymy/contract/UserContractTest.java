package com.lunkes.verifymy.contract;

import com.lunkes.verifymy.base.BaseTest;
import lombok.extern.java.Log;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

@Log
public class UserContractTest extends BaseTest {
    @BeforeClass
    public void setUpTest() {
        deletAllPreviusUsers();
        insertInitialData("src/test/resources/testMass/initialMass.json");
        log.info("Test Mass has been inserted");
    }
    @Test
    public void contracGettUserTest(){
        userClient.getUsers()
                .statusCode(200)
                .body(matchesJsonSchema(
                        new File(PATH_CONTRACT_SCHEMAS + "/contract-get-users-schema.json")
                ));

    }
}
