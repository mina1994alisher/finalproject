package API_Test;

import Utilitis.DataReader;
import Utilitis.utils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class CreateCustomerAPItestNG {

    String baseURI= DataReader.getPropertyValue("craterURL");
    String token;
    Response response;
    Map<String, String> requestHeaders= new HashMap<>();

    String name= utils.name();
    String email = utils.email();
    @Test
    public void login(){


        String endpoint = "/api/v1/auth/login";


        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("Accept", "application/json");
        requestHeaders.put("company", "1");

        Map<String, String> requestBody= new HashMap<>();
        requestBody.put("username", DataReader.getPropertyValue("userName"));
        requestBody.put("password", DataReader.getPropertyValue("userPassword"));
        requestBody.put("device_name", "mobile_app");

        response= RestAssured.given()
                .headers(requestHeaders)
                .body(requestBody)
                .when()
                .post(baseURI+endpoint);
        response.then().statusCode(200);
        token= response.path("token");
        System.out.println(token);

    }

    @Test(dependsOnMethods = "login")
    public void createCustomer(){

        String endpoint="/api/v1/customers";

        requestHeaders.put("Authorization", "Bearer "+token);

        Map<String, String> requestBody= new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("email", email);

        response = RestAssured.given()
                .headers(requestHeaders)
                .body(requestBody)
                .when()
                .post(baseURI+endpoint);
        response.then().statusCode(200);
        response.prettyPrint();
        response.path("data.name", String.valueOf(Matchers.equalTo(name)));
        response.path("data.email", String.valueOf(Matchers.equalTo(email)));



    }

}
