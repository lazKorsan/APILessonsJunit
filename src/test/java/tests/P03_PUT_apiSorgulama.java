package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class P03_PUT_apiSorgulama {

    @Test
    public void TC_01(){
        /*
    https://jsonplaceholder.typicode.com/posts/70 url’ine
    asagidaki Json formatindaki body ile bir PUT request gonderdigimizde
            {
            "title":"Ahmet",
            "body":"Merhaba",
            "userId":10,
            "id":70
            }
        donen Response’un,
         	status code’unun 200,
        	ve content type’inin application/json; charset=utf-8,
        	ve Server isimli Header’in degerinin cloudflare,
        	ve status Line’in HTTP/1.1 200 OK

  */

        String url = "https://jsonplaceholder.typicode.com/posts/70";

        JSONObject reqBody = new JSONObject();
        reqBody.put("title","Ahmet");
        reqBody.put("body","Merhaba");
        reqBody.put("userId",10);
        reqBody.put("id",70);

        System.out.println(reqBody);

        //2-Expected Data (Response) verilmemiş

        //3-Response değerini kayıt altına alma
        Response response=given().contentType(ContentType.JSON).when().body(reqBody.toString()).put(url);

        //4-Assertion
        response.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8")
                .statusLine("HTTP/1.1 200 OK").header("Server","cloudflare");

        //Sonuçta sorunsuz çalışma tamamlanırsa değerler doğrulanmış demektir.




    }
}
