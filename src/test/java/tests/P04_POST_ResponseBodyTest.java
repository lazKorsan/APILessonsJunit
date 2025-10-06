package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.*;

public class P04_POST_ResponseBodyTest {

    String title = "title";
    String api = "API";


    @Test
    public void TC_01(){
         /*
          https://jsonplaceholder.typicode.com/posts url’ine
          asagidaki body ile bir POST request gonderdigimizde
                {
                "title":"API",
                "body":"API ogrenmek ne guzel",
                 "userId":10
                }

        donen Response’un,
        status code’unun 201,
        ve content type’inin application/json
        ve Response Body’sindeki,
           “title”’in “API” oldugunu
           “userId” degerinin 100’den kucuk oldugunu
           “body” nin “API” kelimesi icerdigini
        test edin.
         */
        String title = "title";
        String api = "API";
        String body = "API ogrenmek ne guzel";
        String bodyM = "body";
        String userId ="userId";
        int userIdValue = 10;

String url ="https://jsonplaceholder.typicode.com/posts";
        JSONObject reqBody = new JSONObject();
        reqBody.put(title,api);
        reqBody.put(bodyM,body);
        reqBody.put(userId,userIdValue);
        System.out.println(reqBody);


        //reqBody.put("title","API");
        //reqBody.put("body","API ogrenmek ne guzel");
        //reqBody.put("userId",10);

        System.out.println(reqBody);

        Response response=given().contentType(ContentType.JSON).when().body(reqBody.toString()).post(url);

        int statusCode = response.statusCode();
        System.out.println("Status code: " + statusCode);
        // 63nci satırdaki status code 59ncu satırda actual olarak kayt edildi
        // ve 63ncu satırda kullanıldı

        response.then().assertThat().statusCode(statusCode).contentType("application/json")
                .body("title", equalTo("API"))
                .body("body",containsString("API"))
                .body("userId",Matchers.lessThan(100));



    }
}
