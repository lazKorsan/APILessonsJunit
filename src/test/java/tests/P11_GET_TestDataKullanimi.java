package tests;

import baseUrl.BaseUrlJsonPlace;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import testDatas.TestDatasJPH;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P11_GET_TestDataKullanimi extends BaseUrlJsonPlace {

    @Test
    public void test(){
        // === ADIM 1: EXTENDS BASEURLJSONPLACE - KALITIM BİLGİSİNİ AL ===
        // ↪️ BaseUrlJsonPlace sınıfından specJsonPlace değişkenini miras alır
        // ↪️ BaseUrlJsonPlace'ın @BeforeEach metodu ÖNCEDEN çalışmıştır

        // === ADIM 2: SPECJSONPLACE PATH PARAMETRELERİNİ AYARLA ===
        specJsonPlace.pathParams("pp1","posts","pp2",22);
        // ↪️ Base URL: https://jsonplaceholder.typicode.com
        // ↪️ Tam URL: https://jsonplaceholder.typicode.com/posts/22

        // === ADIM 3: JSONOBJECT EXPBODY DEĞİŞKENİ TANIMLA ===
        JSONObject expBody = TestDatasJPH.jphExpDataOlustur();
        // ↪️ expBody değişkeni tanımlanır, henüz değer atanmamış

        // === ADIM 4: TESTDATASJPH SINIFINA GİT - VERİ TOPLA ===
        // ↪️ Program TestDatasJPH.jphExpDataOlustur() metodunu çalıştırmak için
        //    TestDatasJPH sınıfına gider ve metodu yürütür

        // === ADIM 5: JPHEXPDATAOLUSTUR METODU ÇALIŞTIR ===
        // ↪️ TestDatasJPH sınıfındaki jphExpDataOlustur() metodu çalışır
        // ↪️ Metod içinde JSONObject oluşturulur ve test verileri doldurulur
        // ↪️ Oluşturulan JSONObject expBody'ye atanır ve geri döner

        // === ADIM 6: API REQUEST GÖNDER ===
        Response response = given().spec(specJsonPlace).when().get("/{pp1}/{pp2}");
        // ↪️ ADIM 6.1: given().spec(specJsonPlace) - ADIM 2'de hazırlanan spec'i kullan
        // ↪️ ADIM 6.2: when().get("/{pp1}/{pp2}") - API'ye GET isteği gönder
        // ↪️ Eğer API hata verirse: ADIM 2'ye döner (URL parametrelerini tekrar ayarlar)
        // ↪️ Eğer bağlantı hatası olursa: ADIM 1'e döner (base URL'i kontrol eder)

        // === ADIM 7: RESPONSE'U JSONPATH'E DÖNÜŞTÜR ===
        JsonPath resJP = response.jsonPath();
        // ↪️ Eğer response boş veya geçersizse: ADIM 6'ya döner (API'ye yeniden istek gönderir)

        // === ADIM 8: STATUS CODE KONTROLÜ ===
        assertEquals(TestDatasJPH.basariliStatusCode, response.getStatusCode());
        // ↪️ ADIM 8.1: TestDatasJPH.basariliStatusCode değerini almak için ADIM 4'e gider
        // ↪️ ADIM 8.2: response.getStatusCode() değerini alır
        // ↪️ Eğer eşleşmezse: ADIM 4'e döner (TestDatasJPH'tan veriyi tekrar kontrol eder)

        // === ADIM 9: USERID ALANI KONTROLÜ ===
        assertEquals(expBody.get("userId"), resJP.getInt("userId"));
        // ↪️ ADIM 9.1: expBody.get("userId") - expBody'den userId değerini alır
        // ↪️ Eğer expBody null veya boşsa: ADIM 3'e döner (expBody'yi tekrar oluşturur)
        // ↪️ Eğer değer eşleşmezse: ADIM 5'e döner (TestDatasJPH metodunu tekrar çalıştırır)

        // === ADIM 10: ID ALANI KONTROLÜ ===
        assertEquals(expBody.get("id"), resJP.getInt("id"));
        // ↪️ ADIM 10.1: expBody.get("id") - expBody'den id değerini alır
        // ↪️ Eğer eşleşmezse: ADIM 5'e döner (TestDatasJPH metodunu tekrar çalıştırır)

        // === ADIM 11: TITLE ALANI KONTROLÜ ===
        assertEquals(expBody.get("title"), resJP.getString("title"));
        // ↪️ ADIM 11.1: expBody.get("title") - expBody'den title değerini alır
        // ↪️ Eğer eşleşmezse: ADIM 5'e döner (TestDatasJPH metodunu tekrar çalıştırır)

        // === ADIM 12: BODY ALANI KONTROLÜ ===
        assertEquals(expBody.get("body"), resJP.getString("body"));
        // ↪️ ADIM 12.1: expBody.get("body") - expBody'den body değerini alır
        // ↪️ Eğer eşleşmezse: ADIM 5'e döner (TestDatasJPH metodunu tekrar çalıştırır)

        // === ADIM 13: TEST BAŞARIYLA TAMAMLANDI ===
        // ↪️ Tüm assertion'lar geçti, test passed


        // ==================================================================================
        // < -- ==================Manuel test adımları
        // MANUEL TEST ADIMLARI - POSTMAN
        //TEST SENARYOSU: JSONPlaceholder API GET İsteği Doğrulama
        //
        //📋 TEST ÖN HAZIRLIK
        //1. Postman uygulamasını aç
        //2. Yeni bir request oluştur
        //   - "+" (Create New) butonuna tıkla
        //   - Request adı: "JSONPlaceholder GET Test - Post 22"
        //
        //🔧 TEST KURULUMU
        //3. Request metodunu seç:
        //   - Method: GET
        //
        //4. URL'yi gir:
        //   https://jsonplaceholder.typicode.com/posts/22
        //
        //🎯 TEST YÜRÜTME
        //5. Request'i gönder:
        //   - "Send" butonuna tıkla
        //
        //6. Response'u bekle:
        //   - API'den response gelene kadar bekle
        //
        //✅ DOĞRULAMA ADIMLARI
        //
        //7. STATUS CODE DOĞRULAMA:
        //   - Beklenen: 200 OK
        //   - Yapılacak: Response status kodu 200 mü?
        //   - ✅ Status: 200 OK kontrol et
        //
        //8. RESPONSE BODY DOĞRULAMA:
        //
        //8.1. userId Alanı Kontrolü:
        //   - Beklenen: 3
        //   - Yapılacak: Response body'de "userId" değeri 3 mü?
        //   - ✅ "userId": 3
        //
        //8.2. id Alanı Kontrolü:
        //   - Beklenen: 22
        //   - Yapılacak: Response body'de "id" değeri 22 mi?
        //   - ✅ "id": 22
        //
        //8.3. title Alanı Kontrolü:
        //   - Beklenen: "dolor sint quo a velit explicabo quia nam"
        //   - Yapılacak: Response body'de "title" değeri aynı mı?
        //   - ✅ "title": "dolor sint quo a velit explicabo quia nam"
        //
        //8.4. body Alanı Kontrolü:
        //   - Beklenen: "eos qui et ipsum ipsam suscipit aut\nsed omnis non odio\nexpedita earum mollitia molestiae aut atque rem suscipit\nnam impedit esse"
        //   - Yapılacak: Response body'de "body" değeri aynı mı?
        //   - ✅ "body": "eos qui et ipsum ipsam suscipit aut\nsed omnis non odio\nexpedita earum mollitia molestiae aut atque rem suscipit\nnam impedit esse"
        //
        //📝 BEKLENEN RESPONSE BODY:
        //{
        //    "userId": 3,
        //    "id": 22,
        //    "title": "dolor sint quo a velit explicabo quia nam",
        //    "body": "eos qui et ipsum ipsam suscipit aut\nsed omnis non odio\nexpedita earum mollitia molestiae aut atque rem suscipit\nnam impedit esse"
        //}
        //
        //🎊 TEST SONUCU
        //9. Tüm kontroller başarılı ise:
        //   - ✅ TEST PASSED - Tüm assertion'lar başarılı
        //
        //10. Herhangi bir kontrol başarısız ise:
        //    - ❌ TEST FAILED - Başarısız olan assertion'ı raporla
        //
        //📋 TEST ÇIKTILARI
        //- Response Time: [Gelen response süresi]
        //- Response Size: [Gelen response boyutu]
        //- Content-Type: application/json; charset=utf-8
        // ======================================================================== -->

        // =========================================================================
        // < --  FEATURE DOSYASI  için örnek
        // Feature: JSONPlaceholder API GET Request Test
        //  As a API tester
        //  I want to verify GET request to JSONPlaceholder posts endpoint
        //  So that I can ensure the API returns correct data for specific post
        //
        //  Background:
        //    Given I set the base URL for JSONPlaceholder API
        //
        //  @GET_Request @Positive @Post22
        //  Scenario: Verify GET request returns correct post data for post ID 22
        //    When I send a GET request to "/posts/22"
        //    Then the response status code should be 200
        //    And the response body should contain following values:
        //      | field | value                                                              |
        //      | userId | 3                                                                 |
        //      | id    | 22                                                                |
        //      | title | dolor sint quo a velit explicabo quia nam                         |
        //      | body  | eos qui et ipsum ipsam suscipit aut\nsed omnis non odio\nexpedita earum mollitia molestiae aut atque rem suscipit\nnam impedit esse |
        //
        //  @GET_Request @Negative @InvalidPost
        //  Scenario: Verify GET request returns 404 for non-existent post
        //    When I send a GET request to "/posts/99999"
        //    Then the response status code should be 404
        //
        //  @GET_Request @Positive @AllPosts
        //  Scenario: Verify GET request returns all posts
        //    When I send a GET request to "/posts"
        //    Then the response status code should be 200
        //    And the response should contain an array of posts
        //    And each post should have required fields
        //
        //  @GET_Request @SchemaValidation
        //  Scenario Outline: Verify post schema validation for different post IDs
        //    When I send a GET request to "/posts/<post_id>"
        //    Then the response status code should be 200
        //    And the response schema should match expected JSON structure
        //
        //    Examples:
        //      | post_id |
        //      | 1       |
        //      | 22      |
        //      | 50      |
        //      | 100     |
        //
        //  @GET_Request @DataValidation
        //  Scenario Outline: Verify specific post data validation
        //    When I send a GET request to "/posts/<post_id>"
        //    Then the response status code should be 200
        //    And the response body for post <post_id> should match expected data
        //
        //    Examples:
        //      | post_id | user_id | title_contains                  |
        //      | 1       | 1       | sunt aut                        |
        //      | 22      | 3       | dolor sint                      |
        //      | 45      | 5       | ut numquam                      |
        //
        //  @GET_Request @Performance
        //  Scenario: Verify GET request performance
        //    When I send a GET request to "/posts/22"
        //    Then the response status code should be 200
        //    And the response time should be less than 1000 milliseconds
        //
        //  @GET_Request @HeadersValidation
        //  Scenario: Verify response headers
        //    When I send a GET request to "/posts/22"
        //    Then the response status code should be 200
        //    And the response content-type should be "application/json; charset=utf-8"
        // ============================================================================= -- >




        //  < -- ============================================================================================
        // < -- STEPDEFINITIONS ADIMLARI
        // // StepDefinitions.java
        //public class JSONPlaceholderStepDefinitions {
        //
        //    private Response response;
        //    private String baseUrl = "https://jsonplaceholder.typicode.com";
        //
        //    @Given("I set the base URL for JSONPlaceholder API")
        //    public void setBaseURL() {
        //        // Base URL setup
        //    }
        //
        //    @When("I send a GET request to {string}")
        //    public void sendGetRequest(String endpoint) {
        //        response = given().baseUri(baseUrl).get(endpoint);
        //    }
        //
        //    @Then("the response status code should be {int}")
        //    public void verifyStatusCode(int expectedStatusCode) {
        //        assertEquals(expectedStatusCode, response.getStatusCode());
        //    }
        //
        //    @Then("the response body should contain following values:")
        //    public void verifyResponseBodyValues(DataTable dataTable) {
        //        Map<String, String> expectedValues = dataTable.asMap(String.class, String.class);
        //        JsonPath jsonPath = response.jsonPath();
        //
        //        for (Map.Entry<String, String> entry : expectedValues.entrySet()) {
        //            String field = entry.getKey();
        //            String expectedValue = entry.getValue();
        //            Object actualValue = jsonPath.get(field);
        //
        //            assertEquals(expectedValue, actualValue.toString(),
        //                "Field " + field + " mismatch");
        //        }
        //    }
        //
        //    @Then("the response should contain an array of posts")
        //    public void verifyResponseContainsPostArray() {
        //        List<Object> posts = response.jsonPath().getList("$");
        //        assertTrue(posts.size() > 0, "Response should contain posts array");
        //    }
        //
        //    @Then("each post should have required fields")
        //    public void verifyEachPostHasRequiredFields() {
        //        List<Map<String, Object>> posts = response.jsonPath().getList("$");
        //
        //        for (Map<String, Object> post : posts) {
        //            assertNotNull(post.get("userId"), "Post should have userId");
        //            assertNotNull(post.get("id"), "Post should have id");
        //            assertNotNull(post.get("title"), "Post should have title");
        //            assertNotNull(post.get("body"), "Post should have body");
        //        }
        //    }
        //
        //    @Then("the response time should be less than {long} milliseconds")
        //    public void verifyResponseTime(long maxTime) {
        //        assertTrue(response.getTime() < maxTime,
        //            "Response time should be less than " + maxTime + "ms");
        //    }
        //
        //    @Then("the response content-type should be {string}")
        //    public void verifyContentType(String expectedContentType) {
        //        assertEquals(expectedContentType, response.getContentType());
        //    }
        //}
        // ============================================================================================ -- >
    }
}