package tests;



import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;


public class P01_GET_ApiSorgulama {





    @Test
    public void test(){

        /*
        Tüm api testleri 4 aşamada gerçekleşir
            1-Endpoint ve varsa request Body(PUT-POST-PACTH) Hazırlama
            2-Soruda bize verilmiş ise expectedbody hazırlama
            3-Response kayıt altına alma
            4-Assertion işlemleri
         */

        /*
        https://restful-booker.herokuapp.com/booking/10 url’ine
                bir GET request gonderdigimizde donen Response’un,
 	            status code’unun 200,
	            ve content type’inin application/json; charset=utf-8,
	            ve Server isimli Header’in degerinin Cowboy,
	            ve status Line’in HTTP/1.1 200 OK
	            ve response suresinin 5 sn’den kisa oldugunu manuel olarak test ediniz.

      */

        // 1-Endpoind hazırlama
        String url="https://restful-booker.herokuapp.com/booking/10";

        // 2- Response Body soruda verilmediği için exğected data hazırlanmadı

        // 3-Response kayıt altına alır
        Response response = given().when().get(url);


       // response.prettyPrint();
        //System.out.println(response.prettyPeek());

        //4-Assertion

        // Manuel Kontrol
        System.out.println(response.getStatusCode());  //200
        System.out.println(response.getContentType()); // application/json; charset=utf-8
        System.out.println(response.getHeader("Server")); // Heroku
        System.out.println(response.getStatusLine()); // HTTP/1.1 200 OK
        System.out.println(response.getTime()); // 5000 ms'den kısa olmalı


        //Otomasyonla kontrol
        response.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8")
               .statusLine("HTTP/1.1 200 OK").header("Server","Heroku");


    }

}