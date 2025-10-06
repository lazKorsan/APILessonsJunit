package tests;

import baseUrl.BaseUrlHerOkuApp;
import baseUrl.BaseUrlJsonPlace;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testDatas.TestDatasJPH;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P13_PUT_deSeralization extends BaseUrlJsonPlace {
    /*
               https://jsonplaceholder.typicode.com/posts/70 url'ine
               asagidaki body’e sahip bir PUT request yolladigimizda
               donen response’in response body’sinin
               asagida verilen ile ayni oldugunu test ediniz
               Request Body
               {
                   "title": "Ahmet",
                   "body": "Merhaba",
                   "userId": 10,
                   "id": 70
               }

               Expected Data :
               {
                   "title": "Ahmet",
                   "body": "Merhaba",
                   "userId": 10,
                   "id": 70
               }

        */

    @Test
    public void test01(){
        // 1. ADIM: API endpoint için path parametrelerini belirleme
        // "posts" ve "70" değerlerini path parametresi olarak ayarlar
        // Oluşacak URL: https://jsonplaceholder.typicode.com/posts/70
        specJsonPlace.pathParams("pp1","posts","pp2","70");

        // 2. ADIM: Request body için Map veri yapısı oluşturma
        // TestDatasJPH sınıfındaki metod kullanılarak request body hazırlanır
        Map<String, Object> reqMapBody = TestDatasJPH.jphMapDataOlustur();

        // 3. ADIM: Expected (beklenen) response body için Map oluşturma
        // TestDatasJPH sınıfındaki metod ile beklenen veriler oluşturulur
        Map<String,Object> expMapBody = TestDatasJPH.jphMapDataOlusturPli("Ahmet","Merhaba",10.0,70.0);

        // 4. ADIM: PUT request gönderme ve response'u alma
        // given(): Test konfigürasyonunu başlatır
        // .contentType(ContentType.JSON): Content-Type header'ını JSON olarak ayarlar
        // .spec(specJsonPlace): Önceden tanımlanmış specifikasyonu kullanır
        // .when().body(reqMapBody): Request body'sini ekler
        // .put("/{pp1}/{pp2}"): PUT request'i gönderir
        Response response = given().contentType(ContentType.JSON).spec(specJsonPlace).when().body(reqMapBody).put("/{pp1}/{pp2}");

        // 5. ADIM: Gelen response'u Map formatına dönüştürme
        // Response'u HashMap olarak parse eder
        Map<String,Object> resMAP = response.as(HashMap.class);

        // 6. ADIM: ASSERTION - Beklenen ve gerçek değerleri karşılaştırma
        // Title değerini karşılaştırır
        assertEquals(expMapBody.get("title"), resMAP.get("title"));
        // Body değerini karşılaştırır
        assertEquals(expMapBody.get("body"), resMAP.get("body"));
        // UserId değerini karşılaştırır
        assertEquals(expMapBody.get("userId"), resMAP.get("userId"));
        // Id değerini karşılaştırır
        assertEquals(expMapBody.get("id"), resMAP.get("id"));
    }


    // < -- ====================================================
    // < -- === manuel test adımları
    // MANUEL TEST ADIMLARI - JSONPlaceholder API PUT Request Testi
    //
    //1. TEST HAZIRLIK ADIMLARI
    //- Test dokümanını inceleyerek gereksinimleri anlayın
    //- İnternet bağlantısının olduğundan emin olun
    //- API test aracını hazırlayın (Postman, Insomnia, curl, vs.)
    //- Test edilecek endpoint'i not alın: https://jsonplaceholder.typicode.com/posts/70
    //
    //2. REQUEST BODY HAZIRLAMA
    //- Aşağıdaki JSON formatında request body oluşturun:
    //{
    //    "title": "Ahmet",
    //    "body": "Merhaba",
    //    "userId": 10,
    //    "id": 70
    //}
    //
    //3. HEADER AYARLARI
    //- Content-Type header'ını application/json olarak ayarlayın
    //- Accept header'ını application/json olarak ayarlayın
    //
    //4. API ÇAĞRISI YAPMA
    //- HTTP metodunu PUT olarak seçin
    //- URL alanına şu adresi girin: https://jsonplaceholder.typicode.com/posts/70
    //- Request body kısmına hazırladığınız JSON'u yapıştırın
    //- Send butonuna tıklayarak isteği gönderin
    //
    //5. RESPONSE DOĞRULAMA ADIMLARI
    //- Status Code Kontrolü: Response status code'un 200 olduğunu doğrulayın
    //- Response Body Kontrolü: Gelen response body'sini inceleyin:
    //{
    //    "title": "Ahmet",
    //    "body": "Merhaba",
    //    "userId": 10,
    //    "id": 70
    //}
    //
    //6. BEKLENEN VS GERÇEK DEĞER KARŞILAŞTIRMASI
    //- Title Alanı: "Ahmet" değerini kontrol edin
    //- Body Alanı: "Merhaba" değerini kontrol edin
    //- UserId Alanı: 10 değerini kontrol edin
    //- Id Alanı: 70 değerini kontrol edin
    //
    //7. TEST SONUÇLARINI DOKÜMANTE ETME
    //- Request ve response'ları kaydedin
    //- Karşılaştırma sonuçlarını not alın
    //- Varsa hataları raporlayın
    //- Testin başarılı/başarısız olduğunu belirtin
    //
    //TEST KRİTERLERİ:
    //- BAŞARILI: Tüm alanlar expected data ile tamamen eşleşmeli
    //- BAŞARISIZ: Herhangi bir alanda uyuşmazlık varsa
    // ============================================== -->


    // < -- ======================================================
    // < -- FEATURE DOSYASI
    // # features/jsonplaceholder_put_test.feature
    //Feature: JSONPlaceholder API PUT Request Testi
    //  JSONPlaceholder API'sine PUT request göndererek
    //  post güncelleme işlemini test etme
    //
    //  Scenario: ID'si 70 olan postu güncelleme
    //    Given JSONPlaceholder API endpoint'i hazırlanır
    //    And PUT request için body data oluşturulur
    //    When "posts/70" endpoint'ine PUT request gönderilir
    //    Then Response status code 200 olmalı
    //    And Response body beklenen değerlerle eşleşmeli
    //
    //  Scenario Outline: Farklı ID'ler ile post güncelleme
    //    Given JSONPlaceholder API endpoint'i hazırlanır
    //    And PUT request için "<title>" ve "<body>" değerleri ile body oluşturulur
    //    When "posts/<id>" endpoint'ine PUT request gönderilir
    //    Then Response status code 200 olmalı
    //    And Response title "<title>" olmalı
    //    And Response body "<body>" olmalı
    //    And Response userId <userId> olmalı
    //    And Response id <id> olmalı
    //
    //    Examples:
    //      | title | body     | userId | id |
    //      | Ahmet | Merhaba  | 10     | 70 |
    //      | Ali   | Selam    | 20     | 71 |
    //      | Ayşe  | Merhaba  | 30     | 72 |
    // ================================================= -- >


    // < -- ================================================
    // < -- STEPDEFİNİTİONS
    // // stepdefinitions/JSONPlaceholderStepDefinitions.java
    //package stepdefinitions;
    //public class JSONPlaceholderStepDefinitions {
    //
    //    private RequestSpecification request;
    //    private Response response;
    //    private Map<String, Object> requestBody;
    //    private String endpoint;
    //
    //    @Given("JSONPlaceholder API endpoint'i hazırlanır")
    //    public void jsonplaceholder_api_endpoint_i_hazırlanır() {
    //        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    //        request = RestAssured.given();
    //        request.header("Content-Type", "application/json");
    //    }
    //
    //    @And("PUT request için body data oluşturulur")
    //    public void put_request_için_body_data_oluşturulur() {
    //        requestBody = new HashMap<>();
    //        requestBody.put("title", "Ahmet");
    //        requestBody.put("body", "Merhaba");
    //        requestBody.put("userId", 10);
    //        requestBody.put("id", 70);
    //    }
    //
    //    @And("PUT request için {string} ve {string} değerleri ile body oluşturulur")
    //    public void put_request_için_ve_değerleri_ile_body_oluşturulur(String title, String body) {
    //        requestBody = new HashMap<>();
    //        requestBody.put("title", title);
    //        requestBody.put("body", body);
    //        requestBody.put("userId", 10);
    //        requestBody.put("id", 70);
    //    }
    //
    //    @When("{string} endpoint'ine PUT request gönderilir")
    //    public void endpoint_ine_put_request_gönderilir(String endpoint) {
    //        this.endpoint = endpoint;
    //        response = request.body(requestBody).put(endpoint);
    //    }
    //
    //    @Then("Response status code {int} olmalı")
    //    public void response_status_code_olmalı(Integer expectedStatusCode) {
    //        assertEquals(expectedStatusCode.intValue(), response.getStatusCode());
    //    }
    //
    //    @And("Response body beklenen değerlerle eşleşmeli")
    //    public void response_body_beklenen_değerlerle_eşleşmeli() {
    //        Map<String, Object> responseMap = response.jsonPath().getMap("$");
    //
    //        assertEquals("Ahmet", responseMap.get("title"));
    //        assertEquals("Merhaba", responseMap.get("body"));
    //        assertEquals(10, responseMap.get("userId"));
    //        assertEquals(70, responseMap.get("id"));
    //    }
    //
    //    @And("Response title {string} olmalı")
    //    public void response_title_olmalı(String expectedTitle) {
    //        Map<String, Object> responseMap = response.jsonPath().getMap("$");
    //        assertEquals(expectedTitle, responseMap.get("title"));
    //    }
    //
    //    @And("Response body {string} olmalı")
    //    public void response_body_olmalı(String expectedBody) {
    //        Map<String, Object> responseMap = response.jsonPath().getMap("$");
    //        assertEquals(expectedBody, responseMap.get("body"));
    //    }
    //
    //    @And("Response userId {int} olmalı")
    //    public void response_user_id_olmalı(Integer expectedUserId) {
    //        Map<String, Object> responseMap = response.jsonPath().getMap("$");
    //        assertEquals(expectedUserId.intValue(), responseMap.get("userId"));
    //    }
    //
    //    @And("Response id {int} olmalı")
    //    public void response_id_olmalı(Integer expectedId) {
    //        Map<String, Object> responseMap = response.jsonPath().getMap("$");
    //        assertEquals(expectedId.intValue(), responseMap.get("id"));
    //    }
    //}




    }
