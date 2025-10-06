package tests;

import baseUrl.BaseUrlHerOkuApp;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class P08_HerOkuAppBaseUrlKullanimi extends BaseUrlHerOkuApp {
    //1-  https://restful-booker.herokuapp.com/booking endpointine
    //    bir GET request gonderdigimizde
    //    donen response’un
    //          status code’unun 200 oldugunu
    //          ve Response’ta 1200'den fazla booking oldugunu test edin


@Test
    public void test08(){


        specRestfull.pathParam("pp1","booking");

        Response response=given().spec(specRestfull).when().get("/{pp1}");

        response.then().assertThat().statusCode(200).body("size()", Matchers.greaterThan(1200)); }

}
