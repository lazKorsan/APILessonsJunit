package tests;

import baseUrl.BaseUrlJsonPlace;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class P07_jsonPlaceHolderBaseUrlKullanimi extends BaseUrlJsonPlace {
    /*
    https://jsonplaceholder.typicode.com/posts/44 endpointine
    bir GET request gonderdigimizde donen response’un
        status code’unun 200 oldugunu ve
        “title” degerinin “optio dolor molestias sit”
    oldugunu test edin

     */

    @BeforeEach
    public void setUp(){
        // @BeforeEach anotasyonu sayesinde bu metot, her testten önce çalışır.
        // Bu class'taki testler için ortak olan path parametresi ("posts") burada set edilir.
        // Bu sayede her test metodu için tekrar yazmaya gerek kalmaz.
        specJsonPlace.pathParam("pp1","posts");
    }

    @Test
    public void test07(){
        // setUp metodunda ortak path parametresi ("posts") zaten set edilmişti.
        // Bu teste özel olan ikinci path parametresi ("44") burada ekleniyor.
        specJsonPlace.pathParam("pp2","44");

        // Request gönderilir. URL yapısı: {baseUrl}/{pp1}/{pp2} -> https://jsonplaceholder.typicode.com/posts/44
        Response response=given().spec(specJsonPlace).when().get("/{pp1}/{pp2}");

        // Dönen response test edilir.
        response.then().assertThat().statusCode(200).body("title", Matchers.equalTo("optio dolor molestias sit"));
    }
}
