package tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P06_JsonPathKullanimi {

    @Test
    public void TC_01(){
        /*
            https://restful-booker.herokuapp.com/booking url’ine
            asagidaki body'ye sahip bir POST request gonderdigimizde

                {
                      "firstname" : "Ahmet",
                      "lastname" : “Bulut",
                      "totalprice" : 500,
                      "depositpaid" : false,
                      "bookingdates" : {
                          "checkin" : "2021-06-01",
                          "checkout" : "2021-06-10"
                      },
                      "additionalneeds" : "wi-fi"
                  }

                     donen Response’un,
             status code’unun 200,
             content type’inin applicatio/json,
             response body’sindeki
                    "firstname“in,"Ahmet",
                    ve "lastname“in, "Bulut",
                    ve "totalprice“in,500,
                    ve "depositpaid“in,false,
                    ve "checkin" tarihinin 2021-06-01
                    ve "checkout" tarihinin 2021-06-10
                    ve "additionalneeds“in,"wi-fi"

              oldugunu test edin

  */

        String url = "https://restful-booker.herokuapp.com/booking";

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin","2021-06-01");
        bookingdates.put("checkout","2021-06-10");

        JSONObject reqBody = new JSONObject();
        reqBody.put("firstname","Ahmet");
        reqBody.put("lastname","Bulut");
        reqBody.put("totalprice",500);
        reqBody.put("depositpaid",false);
        reqBody.put("bookingdates",bookingdates);
        reqBody.put("additionalneeds","wi-fi");

        System.out.println(reqBody);

        Response response=given().contentType(ContentType.JSON).when().body(reqBody.toString()).post(url);
        response.prettyPrint();

        JsonPath resJP = response.jsonPath();

        assertEquals(200,response.getStatusCode());
        assertEquals("application/json; charset=utf-8",response.getContentType());
        assertEquals("Ahmet",resJP.get("booking.firstname"));
        assertEquals("Bulut",resJP.get("booking.lastname"));
        assertEquals(false,resJP.get("booking.depositpaid"));
        assertEquals("wi-fi",resJP.get("booking.additionalneeds"));
        assertEquals("2021-06-10",resJP.get("booking.bookingdates.checkout"));
        int resTot=resJP.get("booking.totalprice");
        assertEquals(500,resTot );















    }
}
