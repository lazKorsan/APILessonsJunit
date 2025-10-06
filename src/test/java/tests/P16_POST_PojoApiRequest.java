package tests;

import baseUrl.BaseUrlHerOkuApp;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojos.POJO_expBodyPOJO_JPH;
import pojos.POJO_innerPOJO_JPH;
import pojos.POJO_reqBodyPOJO_JPH;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P16_POST_PojoApiRequest extends BaseUrlHerOkuApp {
    /*
       https://restful-booker.herokuapp.com/booking url’ine
       asagidaki body'ye sahip bir POST request gonderdigimizde
       donen response’un id haric asagidaki gibi oldugunu test edin.

       Request body
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

      Response Body // expected data
{
   "bookingid": 24,
   "booking": {
       "firstname": "Ahmet",
       "lastname": "Bulut",
       "totalprice": 500,
       "depositpaid": false,
       "bookingdates": {
           "checkin": "2021-06-01",
           "checkout": "2021-06-10"
       },
       "additionalneeds": "wi-fi"
   }
}
 */
    @Test
    public void test(){
        specRestfull.pathParam("pp1","booking");
        POJO_innerPOJO_JPH bookingdates=new POJO_innerPOJO_JPH("2025-01-01","2025-01-10");
        POJO_reqBodyPOJO_JPH reqBody=new POJO_reqBodyPOJO_JPH("Murat","Babayiğit",500,true,bookingdates,"wi-fi");

        POJO_expBodyPOJO_JPH expBody=new POJO_expBodyPOJO_JPH(25,reqBody);

        Response response=given().spec(specRestfull).contentType(ContentType.JSON).when().body(reqBody).post("/{pp1}");

        POJO_expBodyPOJO_JPH resPOJO=response.as(POJO_expBodyPOJO_JPH.class);

        assertEquals(expBody.getBooking().getFirstname(),resPOJO.getBooking().getFirstname());
        assertEquals(expBody.getBooking().getLastname(),resPOJO.getBooking().getLastname());
        assertEquals(expBody.getBooking().getTotalprice(),resPOJO.getBooking().getTotalprice());
        assertEquals(expBody.getBooking().isDepositpaid(),resPOJO.getBooking().isDepositpaid());
        assertEquals(expBody.getBooking().getAdditionalneeds(),resPOJO.getBooking().getAdditionalneeds());
        assertEquals(expBody.getBooking().getBookingdates().getCheckin(),resPOJO.getBooking().getBookingdates().getCheckin());
        assertEquals(expBody.getBooking().getBookingdates().getCheckout(),resPOJO.getBooking().getBookingdates().getCheckout());


        // # Tüm testler
        //mvn test
        //
        //# Smoke testler
        //mvn test -Dtags="@smoke"
        //
        //# Regression testler
        //mvn test -Dtags="@regression"
        //
        //# Paralel test
        //mvn test -Dcucumber.execution.parallel.enabled=true
        //
        //# Profile ile
        //mvn test -Psmoke
        //mvn test -Pregression
        //mvn test -Papi
        //
        //# Belirli runner
        //mvn test -Dtest=TestRunner
        //mvn test -Dtest=SmokeTestRunner
    }
}