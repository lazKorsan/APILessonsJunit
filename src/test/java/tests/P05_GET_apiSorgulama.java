package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class P05_GET_apiSorgulama {

    String url = "https://restful-booker.herokuapp.com/booking/10";
    String firstname = "firstname";
    String lastname = "lastname";
    String totalprice = "totalprice";
    String depositpaid = "depositpaid";
    String additionalneeds = "additionalneeds";


    @Test
    public void TC_01(){


        /*
    https://restful-booker.herokuapp.com/booking/10 url’ine bir GET request gonderdigimizde donen Response’un,
 	status code’unun 200,
	ve content type’inin application/json,
	ve response body’sindeki
		"firstname“in,"Susan",
	ve "lastname“in, "Jackson",
	ve "totalprice“in,612,
	ve "depositpaid“in,false,
	ve "additionalneeds“in,"Breakfast"
oldugunu test edin
 */
        JSONObject reqBody = new JSONObject();
        reqBody.put(firstname,"Susan");
        reqBody.put(lastname,"Jackson");
        reqBody.put(totalprice,612);
        reqBody.put(depositpaid,false);
        reqBody.put(additionalneeds,"Breakfast");

        System.out.println(reqBody);

        Response response=given().when().get(url);
        response.then().assertThat().statusCode(200).contentType("application/json")
                .body("firstname", equalTo("Susan"),"lastname",equalTo("Jones"),
                        "totalprice",equalTo(100),"depositpaid",equalTo(false),
                        "additionalneeds",equalTo(null));







    }
}
