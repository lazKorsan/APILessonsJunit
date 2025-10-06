package tests;

import baseUrl.BaseUrlHerOkuApp;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testDatas.TestDatasHerOkuApp;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P14_POST_deSeralization extends BaseUrlHerOkuApp {

    // 📝 Step Definitions → 📦 Request Body → 🌐 HEROKU APP API → 📨 Response → ✅ Assertions
    // 🔄 DETAYLI ÇALIŞMA ADIMLARI:
    //HAZIRLIK → Base URL ve header'lar ayarlanır
    //
    //VERİ OLUŞTURMA → Request body Map formatında hazırlanır
    //
    //DIŞ İSTEK → HEROKU APP'e POST isteği gönderilir
    //
    //RESPONSE ALMA → API'den dönen response alınır
    //
    //DE-SERIALIZATION → JSON response Map'e çevrilir
    //
    //VALIDATION → Tüm alanlar tek tek kontrol edilir
    //
    //RAPORLAMA → Test sonuçları konsola yazdırılır
    /*
        https://restful-booker.herokuapp.com/booking url'ine
        asagidaki body'ye sahip bir POST request gonderdigimizde
        donen response'un id haric asagidaki gibi oldugunu test edin.
     */

    // SORUSU :
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
                        "bookingid":24,
                        "booking":{
                            "firstname":"Ahmet",
                            "lastname":"Bulut",
                            "totalprice":500,
                            "depositpaid":false,
                            "bookingdates":{
                                "checkin":"2021-06-01",
                                "checkout":"2021-06-10"
                            ,
                            "additionalneeds":"wi-fi"
                        }
         */
    @Test
    public void test(){
        // 🔹 ADIM 1: API endpoint hazırlama (HEROKU APP'e gidiyor)
        // BaseUrlHerOkuApp'ten gelen specRestfull'u kullanarak
        // "booking" path parametresini ekliyoruz
        specRestfull.pathParam("pp1","booking");

        // 🔹 ADIM 2: Request body oluşturma (TestDatasHerOkuApp'ten veri alıyor)
        // TestDatasHerOkuApp sınıfından request body map'ini alıyoruz
        Map<String,Object> reqMAPBody= TestDatasHerOkuApp.mapDataOlustur();

        // 🔹 ADIM 3: Expected data oluşturma (TestDatasHerOkuApp'ten veri alıyor)
        // TestDatasHerOkuApp sınıfından expected response map'ini alıyoruz
        Map<String,Object> expMAPBody=TestDatasHerOkuApp.expDataOlustur();

        // 🔹 ADIM 4: POST request gönderme (HEROKU APP'e gidiyor - DIŞ AĞ İSTEĞİ)
        // given(): Test konfigürasyonu başlatır
        // .spec(specRestfull): Base URL ve path parametrelerini kullanır
        // .contentType(ContentType.JSON): Content-Type header'ını JSON olarak ayarlar
        // .when().body(reqMAPBody): Request body'sini ekler
        // .post("/{pp1}"): POST request'i gönderir → HEROKU APP'e gidiyor!
        Response response=given().spec(specRestfull).contentType(ContentType.JSON).when().body(reqMAPBody).post("/{pp1}");

        // 🔹 ADIM 5: Status code kontrolü (TestDatasHerOkuApp'ten veri alıyor)
        // Response'dan gelen status code'u beklenen değerle karşılaştırır
        assertEquals(TestDatasHerOkuApp.basariliStatusCode,response.getStatusCode());

        // 🔹 ADIM 6: Response'u Map'e dönüştürme (De-Serialization)
        // Response body'sini HashMap formatına çeviriyoruz
        Map<String,Object> resMAP=response.as(HashMap.class);

        // 🔹 ADIM 7: Response body assertion'ları (HEROKU APP'ten dönen verileri kontrol ediyor)
        // Firstname kontrolü: HEROKU APP'ten dönen firstname'i karşılaştırır
        assertEquals(((Map)expMAPBody.get("booking")).get("firstname"),((Map)resMAP.get("booking")).get("firstname"));

        // Lastname kontrolü: HEROKU APP'ten dönen lastname'i karşılaştırır
        assertEquals(((Map)expMAPBody.get("booking")).get("lastname"),((Map)resMAP.get("booking")).get("lastname"));

        // Totalprice kontrolü: HEROKU APP'ten dönen totalprice'ı karşılaştırır
        assertEquals(((Map)expMAPBody.get("booking")).get("totalprice"),((Map)resMAP.get("booking")).get("totalprice"));

        // Depositpaid kontrolü: HEROKU APP'ten dönen depositpaid'i karşılaştırır
        assertEquals(((Map)expMAPBody.get("booking")).get("depositpaid"),((Map)resMAP.get("booking")).get("depositpaid"));

        // Additionalneeds kontrolü: HEROKU APP'ten dönen additionalneeds'i karşılaştırır
        assertEquals(((Map)expMAPBody.get("booking")).get("additionalneeds"),((Map)resMAP.get("booking")).get("additionalneeds"));

        // Checkin tarihi kontrolü: HEROKU APP'ten dönen checkin tarihini karşılaştırır
        assertEquals(((Map)((Map)expMAPBody.get("booking")).get("bookingdates")).get("checkin"),((Map)((Map)resMAP.get("booking")).get("bookingdates")).get("checkin"));

        // Checkout tarihi kontrolü: HEROKU APP'ten dönen checkout tarihini karşılaştırır
        assertEquals(((Map)((Map)expMAPBody.get("booking")).get("bookingdates")).get("checkout"),((Map)((Map)resMAP.get("booking")).get("bookingdates")).get("checkout"));

        // 🔄 DIŞTAN GELEN VERİLER:
        //TestDatasHerOkuApp.mapDataOlustur() → Request body verisi
        //TestDatasHerOkuApp.expDataOlustur() → Expected response verisi
        //TestDatasHerOkuApp.basariliStatusCode → Status code değeri
        //HEROKU APP Response → Gerçek API response'u

   // < -- ==================================================
   // < -- MANUEL TEST ADİMLARİ
   // MANUEL TEST ADIMLARI - HerokuApp Booking API POST Request Testi
        //
        //1. TEST ÖN HAZIRLIK
        //- Test senaryosunu anlama: HerokuApp Booking API'ye POST request
        //- İnternet bağlantısı kontrolü
        //- API test aracı hazırlama (Postman, Insomnia, vs.)
        //- Endpoint not et: https://restful-booker.herokuapp.com/booking
        //
        //2. REQUEST BODY HAZIRLAMA
        //- Aşağıdaki JSON formatında request body oluştur:
        //{
        //    "firstname": "Ahmet",
        //    "lastname": "Bulut",
        //    "totalprice": 500,
        //    "depositpaid": false,
        //    "bookingdates": {
        //        "checkin": "2021-06-01",
        //        "checkout": "2021-06-10"
        //    },
        //    "additionalneeds": "wi-fi"
        //}
        //
        //3. HEADER KONFİGÜRASYONU
        //- Content-Type: application/json
        //- Accept: application/json
        //
        //4. API İSTEĞİ GÖNDERME
        //- HTTP Method: POST
        //- URL: https://restful-booker.herokuapp.com/booking
        //- Body: Hazırlanan JSON'u ekle
        //- "Send" butonuna tıkla
        //
        //5. RESPONSE DOĞRULAMA ADIMLARI
        //
        //5.1 STATUS CODE KONTROLÜ
        //- Response Status Code: 200 olmalı
        //
        //5.2 RESPONSE BODY YAPISAL KONTROL
        //- Response'un aşağıdaki structure'a sahip olduğunu kontrol et:
        //{
        //    "bookingid": [number],
        //    "booking": {
        //        "firstname": "Ahmet",
        //        "lastname": "Bulut",
        //        "totalprice": 500,
        //        "depositpaid": false,
        //        "bookingdates": {
        //            "checkin": "2021-06-01",
        //            "checkout": "2021-06-10"
        //        },
        //        "additionalneeds": "wi-fi"
        //    }
        //}
        //
        //5.3 DETAYLI ALAN KONTROLLERİ
        //
        //5.3.1 BOOKING OBJECT İÇİNDEKİ ALANLAR
        //- firstname: "Ahmet" değerini kontrol et
        //- lastname: "Bulut" değerini kontrol et
        //- totalprice: 500 değerini kontrol et
        //- depositpaid: false değerini kontrol et
        //- additionalneeds: "wi-fi" değerini kontrol et
        //
        //5.3.2 BOOKINGDATES OBJECT İÇİNDEKİ ALANLAR
        //- bookingdates.checkin: "2021-06-01" değerini kontrol et
        //- bookingdates.checkout: "2021-06-10" değerini kontrol et
        //
        //5.4 BOOKINGID KONTROLÜ
        //- bookingid alanının numeric bir değer olduğunu kontrol et
        //- bookingid'nin her request'te unique olduğunu not et
        //
        //6. ÖZEL DURUM TESTLERİ (OPSİYONEL)
        //
        //6.1 ZORUNLU ALAN TESTLERİ
        //- firstname eksik: Hata response'u kontrol et
        //- lastname eksik: Hata response'u kontrol et
        //- totalprice eksik: Hata response'u kontrol et
        //- bookingdates eksik: Hata response'u kontrol et
        //
        //6.2 FORMAT TESTLERİ
        //- Geçersiz tarih formatı: checkin "2021-13-01"
        //- Geçersiz tarih formatı: checkout "2021-06-32"
        //- String yerine number: firstname 123 gönder
        //
        //7. TEST DOKÜMANTASYONU
        //
        //7.1 KAYIT ALTINA ALINACAKLAR
        //- Request URL ve body
        //- Response status code
        //- Response body tam içeriği
        //- bookingid değeri
        //- Test tarih ve saati
        //- Test ortamı bilgileri
        //
        //7.2 BEKLENEN DAVRANIŞLAR
        //- Status code her zaman 200 olmalı
        //- bookingid her request'te farklı olmalı
        //- Tüm booking bilgileri request'te gönderilenle aynı olmalı
        //- bookingdates object'i doğru nested structure'da olmalı
        //
        //8. TEST KRİTERLERİ DEĞERLENDİRMESİ
        //
        //✅ BAŞARILI KRİTERLER:
        //- Status code 200
        //- Tüm alanlar expected değerlerle eşleşmeli
        //- bookingid numeric ve unique
        //- Response structure doğru
        //
        //❌ BAŞARISIZ KRİTERLER:
        //- Status code 200 değil
        //- Herhangi bir alanda değer uyuşmazlığı
        //- Response structure hatalı
        //- bookingid dönmüyor
        //
        //9. GÜVENLİK TESTLERİ (OPSİYONEL)
        //- SQL injection denemeleri
        //- XSS payload testleri
        //- Büyük data gönderimi (DDOS simülasyonu)
        //
        //NOT: Bu test HerokuApp'in demo API'sini kullanmaktadır.
        //Gerçek veritabanı değişikliği yapmaz, geçici response döner.
    // ========================================== -->


    // <-- ==================================================
    // < -- FEATURE DOSYASI
    // # features/herokuapp_booking.feature
        //Özellik: HerokuApp Booking API POST İsteği Testi
        //  HerokuApp Booking API'sine POST isteği göndererek
        //  yeni bir booking oluşturma işlemini test etme
        //
        //  Senaryo: Yeni booking oluşturma ve response doğrulama
        //    Verki HerokuApp Booking API endpoint'i hazırlanmış olsun
        //    Ve POST isteği için gerekli body datası oluşturulmuş olsun
        //    Eğer "booking" endpoint'ine POST isteği gönderildiğinde
        //    O zaman response status kodu 200 olmalı
        //    Ve response body'si beklenen değerlerle eşleşmeli
        //
        //  Senaryo Taslağı: Farklı booking verileri ile oluşturma testi
        //    Verki HerokuApp Booking API endpoint'i hazırlanmış olsun
        //    Ve POST isteği için "<firstname>", "<lastname>", "<totalprice>" değerleri ile body oluşturulmuş olsun
        //    Eğer "booking" endpoint'ine POST isteği gönderildiğinde
        //    O zaman response status kodu 200 olmalı
        //    Ve response firstname "<firstname>" olmalı
        //    Ve response lastname "<lastname>" olmalı
        //    Ve response totalprice <totalprice> olmalı
        //    Ve response depositpaid <depositpaid> olmalı
        //    Ve response additionalneeds "<additionalneeds>" olmalı
        //    Ve response checkin "<checkin>" olmalı
        //    Ve response checkout "<checkout>" olmalı
        //
        //    Örnekler:
        //      | firstname | lastname | totalprice | depositpaid | additionalneeds | checkin      | checkout     |
        //      | Ahmet     | Bulut    | 500        | false       | wi-fi           | 2021-06-01   | 2021-06-10   |
        //      | Ali       | Yılmaz   | 750        | true        | kahvaltı        | 2021-07-15   | 2021-07-20   |
        //      | Ayşe      | Demir    | 300        | false       | park            | 2021-08-01   | 2021-08-05   |
        //
        //  Senaryo: Zorunlu alanların eksik gönderilmesi durumu
        //    Verki HerokuApp Booking API endpoint'i hazırlanmış olsun
        //    Ve POST isteği için firstname olmadan body oluşturulmuş olsun
        //    Eğer "booking" endpoint'ine POST isteği gönderildiğinde
        //    O zaman response status kodu 400 olmalı
        //    Ve response hata mesajı içermeli
        //
        //  Senaryo: Geçersiz tarih formatı ile istek gönderme
        //    Verki HerokuApp Booking API endpoint'i hazırlanmış olsun
        //    Ve POST isteği için geçersiz checkin tarihi ile body oluşturulmuş olsun
        //    Eğer "booking" endpoint'ine POST isteği gönderildiğinde
        //    O zaman response status kodu 400 olmalı
        //    Ve response tarih formatı hatası içermeli


    // < -- ==============================================
    // < -- FEATURE NEGATİF TEST SENARYOSU
    // # features/herokuapp_booking_negative.feature
        //Özellik: HerokuApp Booking API Negatif Test Senaryoları
        //  HerokuApp Booking API'sine yapılan isteklerde
        //  hata durumlarını ve validasyonları test etme
        //
        //  Senaryo: Eksik firstname ile istek gönderme
        //    Verki API endpoint'i hazırlanmış olsun
        //    Ve firstname alanı eksik olarak body oluşturulmuş olsun
        //    Eğer booking oluşturma isteği gönderildiğinde
        //    O zaman response status kodu 400 olmalı
        //    Ve response firstname zorunlu alan hatası içermeli
        //
        //  Senaryo: Eksik lastname ile istek gönderme
        //    Verki API endpoint'i hazırlanmış olsun
        //    Ve lastname alanı eksik olarak body oluşturulmuş olsun
        //    Eğer booking oluşturma isteği gönderildiğinde
        //    O zaman response status kodu 400 olmalı
        //    Ve response lastname zorunlu alan hatası içermeli
        //
        //  Senaryo: Eksik totalprice ile istek gönderme
        //    Verki API endpoint'i hazırlanmış olsun
        //    Ve totalprice alanı eksik olarak body oluşturulmuş olsun
        //    Eğer booking oluşturma isteği gönderildiğinde
        //    O zaman response status kodu 400 olmalı
        //    Ve response totalprice zorunlu alan hatası içermeli
        //
        //  Senaryo: Geçersiz totalprice değeri ile istek gönderme
        //    Verki API endpoint'i hazırlanmış olsun
        //    Ve totalprice alanı negatif değer içeren body oluşturulmuş olsun
        //    Eğer booking oluşturma isteği gönderildiğinde
        //    O zaman response status kodu 400 olmalı
        //    Ve response totalprice geçersiz değer hatası içermeli
        //
        //  Senaryo: Geçersiz tarih aralığı ile istek gönderme
        //    Verki API endpoint'i hazırlanmış olsun
        //    Ve checkout tarihi checkin tarihinden önce olacak şekilde body oluşturulmuş olsun
        //    Eğer booking oluşturma isteği gönderildiğinde
        //    O zaman response status kodu 400 olmalı
        //    Ve response tarih uyumsuzluğu hatası içermeli


    // < -- ===========================================
    // < -- STEPDEFİNİTİONS
    // package stepdefinitions;
        //
        //import io.cucumber.java.en.Given;
        //import io.cucumber.java.en.When;
        //import io.cucumber.java.en.Then;
        //import io.cucumber.java.en.And;
        //import io.restassured.RestAssured;
        //import io.restassured.response.Response;
        //import io.restassured.specification.RequestSpecification;
        //import java.util.HashMap;
        //import java.util.Map;
        //import static org.junit.Assert.assertEquals;
        //
        //public class HerokuAppBookingStepDefinitions {
        //
        //    private RequestSpecification request;
        //    private Response response;
        //    private Map<String, Object> requestBody;
        //    private String endpoint;
        //
        //    // 🔹 ADIM 1: API endpoint hazırlama (HEROKU APP'e bağlantı öncesi hazırlık)
        //    @Given("HerokuApp Booking API endpoint'i hazırlanmış olsun")
        //    public void herokuapp_booking_api_endpoint_i_hazırlanmış_olsun() {
        //        // Base URL ayarlanıyor - HEROKU APP'e bağlanacak
        //        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        //        request = RestAssured.given();
        //        // Content-Type header'ı JSON olarak ayarlanıyor
        //        request.header("Content-Type", "application/json");
        //        System.out.println("🔧 API endpoint hazırlandı: " + RestAssured.baseURI);
        //    }
        //
        //    // 🔹 ADIM 2: Request body oluşturma (Test verileri hazırlanıyor)
        //    @And("POST isteği için gerekli body datası oluşturulmuş olsun")
        //    public void post_isteği_için_gerekli_body_datası_oluşturulmuş_olsun() {
        //        requestBody = new HashMap<>();
        //        requestBody.put("firstname", "Ahmet");
        //        requestBody.put("lastname", "Bulut");
        //        requestBody.put("totalprice", 500);
        //        requestBody.put("depositpaid", false);
        //
        //        // 🔹 İç içe Map oluşturma (bookingdates objesi)
        //        Map<String, String> bookingDates = new HashMap<>();
        //        bookingDates.put("checkin", "2021-06-01");
        //        bookingDates.put("checkout", "2021-06-10");
        //
        //        requestBody.put("bookingdates", bookingDates);
        //        requestBody.put("additionalneeds", "wi-fi");
        //
        //        System.out.println("📦 Request body oluşturuldu: " + requestBody);
        //    }
        //
        //    // 🔹 ADIM 3: Dinamik request body oluşturma (Scenario Outline için)
        //    @And("POST isteği için {string}, {string}, {int} değerleri ile body oluşturulmuş olsun")
        //    public void post_isteği_için_değerleri_ile_body_oluşturulmuş_olsun(String firstname, String lastname, Integer totalprice) {
        //        requestBody = new HashMap<>();
        //        requestBody.put("firstname", firstname);
        //        requestBody.put("lastname", lastname);
        //        requestBody.put("totalprice", totalprice);
        //        requestBody.put("depositpaid", false);
        //
        //        Map<String, String> bookingDates = new HashMap<>();
        //        bookingDates.put("checkin", "2021-06-01");
        //        bookingDates.put("checkout", "2021-06-10");
        //
        //        requestBody.put("bookingdates", bookingDates);
        //        requestBody.put("additionalneeds", "wi-fi");
        //
        //        System.out.println("📦 Dinamik request body oluşturuldu: " + requestBody);
        //    }
        //
        //    // 🔹 ADIM 4: POST isteği gönderme (HEROKU APP'e DIŞ AĞ İSTEĞİ)
        //    @When("booking endpoint'ine POST isteği gönderildiğinde")
        //    public void booking_endpoint_ine_post_isteği_gönderildiğinde() {
        //        this.endpoint = "booking";
        //        // 🔹 DIŞ AĞ İSTEĞİ: HEROKU APP'e POST isteği gönderiliyor
        //        response = request.body(requestBody).post("/" + endpoint);
        //        System.out.println("🚀 POST isteği gönderildi: " + RestAssured.baseURI + "/" + endpoint);
        //        System.out.println("📨 Response Status Code: " + response.getStatusCode());
        //    }
        //
        //    // 🔹 ADIM 5: Status code kontrolü (HEROKU APP'ten dönen response kontrolü)
        //    @Then("response status kodu {int} olmalı")
        //    public void response_status_kodu_olmalı(Integer expectedStatusCode) {
        //        // HEROKU APP'ten dönen status code'u kontrol et
        //        assertEquals(expectedStatusCode.intValue(), response.getStatusCode());
        //        System.out.println("✅ Status code doğrulandı: " + expectedStatusCode);
        //    }
        //
        //    // 🔹 ADIM 6: Tüm response body kontrolü (HEROKU APP'ten dönen verilerin tamamı)
        //    @And("response body'si beklenen değerlerle eşleşmeli")
        //    public void response_body_si_beklenen_değerlerle_eşleşmeli() {
        //        // Response'u Map formatına çevir (De-Serialization)
        //        Map<String, Object> responseMap = response.jsonPath().getMap("$");
        //
        //        // 🔹 Booking objesini içinden çıkar
        //        Map<String, Object> bookingMap = (Map<String, Object>) responseMap.get("booking");
        //
        //        // 🔹 Tüm alanları tek tek kontrol et
        //        assertEquals("Ahmet", bookingMap.get("firstname"));
        //        assertEquals("Bulut", bookingMap.get("lastname"));
        //        assertEquals(500, bookingMap.get("totalprice"));
        //        assertEquals(false, bookingMap.get("depositpaid"));
        //        assertEquals("wi-fi", bookingMap.get("additionalneeds"));
        //
        //        // 🔹 Bookingdates objesini içinden çıkar ve kontrol et
        //        Map<String, Object> bookingDatesMap = (Map<String, Object>) bookingMap.get("bookingdates");
        //        assertEquals("2021-06-01", bookingDatesMap.get("checkin"));
        //        assertEquals("2021-06-10", bookingDatesMap.get("checkout"));
        //
        //        // 🔹 BookingID kontrolü (HEROKU APP'ten dönen unique ID)
        //        Object bookingId = responseMap.get("bookingid");
        //        System.out.println("🎫 Booking ID: " + bookingId);
        //
        //        System.out.println("✅ Tüm response body değerleri doğrulandı");
        //    }
        //
        //    // 🔹 ADIM 7: Tekil alan kontrolleri (Scenario Outline için)
        //    @And("response firstname {string} olmalı")
        //    public void response_firstname_olmalı(String expectedFirstname) {
        //        Map<String, Object> responseMap = response.jsonPath().getMap("$");
        //        Map<String, Object> bookingMap = (Map<String, Object>) responseMap.get("booking");
        //        assertEquals(expectedFirstname, bookingMap.get("firstname"));
        //        System.out.println("✅ Firstname doğrulandı: " + expectedFirstname);
        //    }
        //
        //    @And("response lastname {string} olmalı")
        //    public void response_lastname_olmalı(String expectedLastname) {
        //        Map<String, Object> responseMap = response.jsonPath().getMap("$");
        //        Map<String, Object> bookingMap = (Map<String, Object>) responseMap.get("booking");
        //        assertEquals(expectedLastname, bookingMap.get("lastname"));
        //        System.out.println("✅ Lastname doğrulandı: " + expectedLastname);
        //    }
        //
        //    @And("response totalprice {int} olmalı")
        //    public void response_totalprice_olmalı(Integer expectedTotalprice) {
        //        Map<String, Object> responseMap = response.jsonPath().getMap("$");
        //        Map<String, Object> bookingMap = (Map<String, Object>) responseMap.get("booking");
        //        assertEquals(expectedTotalprice.intValue(), bookingMap.get("totalprice"));
        //        System.out.println("✅ Totalprice doğrulandı: " + expectedTotalprice);
        //    }
        //
        //    @And("response depositpaid {string} olmalı")
        //    public void response_depositpaid_olmalı(String expectedDepositpaid) {
        //        Map<String, Object> responseMap = response.jsonPath().getMap("$");
        //        Map<String, Object> bookingMap = (Map<String, Object>) responseMap.get("booking");
        //        Boolean expectedValue = Boolean.parseBoolean(expectedDepositpaid);
        //        assertEquals(expectedValue, bookingMap.get("depositpaid"));
        //        System.out.println("✅ Depositpaid doğrulandı: " + expectedDepositpaid);
        //    }
        //
        //    @And("response additionalneeds {string} olmalı")
        //    public void response_additionalneeds_olmalı(String expectedAdditionalneeds) {
        //        Map<String, Object> responseMap = response.jsonPath().getMap("$");
        //        Map<String, Object> bookingMap = (Map<String, Object>) responseMap.get("booking");
        //        assertEquals(expectedAdditionalneeds, bookingMap.get("additionalneeds"));
        //        System.out.println("✅ Additionalneeds doğrulandı: " + expectedAdditionalneeds);
        //    }
        //
        //    @And("response checkin {string} olmalı")
        //    public void response_checkin_olmalı(String expectedCheckin) {
        //        Map<String, Object> responseMap = response.jsonPath().getMap("$");
        //        Map<String, Object> bookingMap = (Map<String, Object>) responseMap.get("booking");
        //        Map<String, Object> bookingDatesMap = (Map<String, Object>) bookingMap.get("bookingdates");
        //        assertEquals(expectedCheckin, bookingDatesMap.get("checkin"));
        //        System.out.println("✅ Checkin doğrulandı: " + expectedCheckin);
        //    }
        //
        //    @And("response checkout {string} olmalı")
        //    public void response_checkout_olmalı(String expectedCheckout) {
        //        Map<String, Object> responseMap = response.jsonPath().getMap("$");
        //        Map<String, Object> bookingMap = (Map<String, Object>) responseMap.get("booking");
        //        Map<String, Object> bookingDatesMap = (Map<String, Object>) bookingMap.get("bookingdates");
        //        assertEquals(expectedCheckout, bookingDatesMap.get("checkout"));
        //        System.out.println("✅ Checkout doğrulandı: " + expectedCheckout);
        //    }
        //}




    }
}