package tests;

import baseUrl.BaseUrlJsonPlace;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojos.POJO_pojoBody_JPH;
import testDatas.TestDatasJPH;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


    /* /*
            https://jsonplaceholder.typicode.com/posts/70 url'ine
            asagidaki body’e sahip bir PUT request yolladigimizda
            donen response’in
            status kodunun 200,
            content type’inin “application/json; charset=utf-8”,
            Connection header degerinin “keep-alive”
            ve response body’sinin asagida verilen ile ayni oldugunu test ediniz
             Request Body
                {
                "title":"Ahmet",
                "body":"Merhaba",
                "userId":10,
                "id":70
                }
            Response body : // expected data
                {
                "title":"Ahmet",
                "body":"Merhaba",
                "userId":10,
                "id":70
                }
         */




    public class P15_PUT_PojoApiRequest extends BaseUrlJsonPlace {

        @Test
        public void test(){
            // 🔹 ADIM 1: API ENDPOINT HAZIRLAMA (JSONPlaceholder'a bağlanacak)
            // BaseUrlJsonPlace'dan gelen specJsonPlace'ı kullanarak
            // "posts" ve "70" path parametrelerini ekliyoruz
            // OLUŞACAK URL: https://jsonplaceholder.typicode.com/posts/70
            specJsonPlace.pathParams("pp1","posts","pp2","70");

            // 🔹 ADIM 2: REQUEST BODY OLUŞTURMA (POJO kullanarak)
            // POJO_pojoBody_JPH sınıfından request body objesi oluşturuluyor
            // Bu obje JSON formatına otomatik dönüştürülecek
            POJO_pojoBody_JPH reqBody = new POJO_pojoBody_JPH("Ahmet","Merhaba",10,70);

            // 🔹 ADIM 3: EXPECTED BODY OLUŞTURMA (POJO kullanarak)
            // Beklenen response verisi için aynı POJO sınıfı kullanılıyor
            POJO_pojoBody_JPH expBody = new POJO_pojoBody_JPH("Ahmet","Merhaba",10,70);

            // 🔹 ADIM 4: PUT REQUEST GÖNDERME (JSONPlaceholder'a DIŞ AĞ İSTEĞİ)
            // given(): Test konfigürasyonu başlatır
            // .spec(specJsonPlace): Base URL ve path parametrelerini kullanır
            // .contentType(ContentType.JSON): Content-Type header'ını JSON olarak ayarlar
            // .when().body(reqBody): POJO objesini JSON body'ye çevirir
            // .put("/{pp1}/{pp2}"): PUT request'i gönderir → JSONPlaceholder'a gidiyor!
            Response response = given().spec(specJsonPlace).contentType(ContentType.JSON).when().body(reqBody).put("/{pp1}/{pp2}");

            // 🔹 ADIM 5: RESPONSE HEADER DOĞRULAMALARI
            // Status code kontrolü: 200 olmalı
            assertEquals(TestDatasJPH.basariliStatusCode, response.getStatusCode());
            // Content-Type kontrolü: "application/json; charset=utf-8" olmalı
            assertEquals(TestDatasJPH.contentType, response.getContentType());
            // Connection header kontrolü: "keep-alive" olmalı
            assertEquals(TestDatasJPH.hconnectionHeader, response.getHeader("Connection"));

            // 🔹 ADIM 6: RESPONSE BODY'YI POJO'YA DÖNÜŞTÜRME (DE-SERIALIZATION)
            // Gelen JSON response'u POJO_pojoBody_JPH sınıfına otomatik çevir
            POJO_pojoBody_JPH resMAP = response.as(POJO_pojoBody_JPH.class);

            // 🔹 ADIM 7: POJO ALANLARINI KARŞILAŞTIRMA
            // Title değerini karşılaştır
            assertEquals(expBody.getTitle(), resMAP.getTitle());
            // Body değerini karşılaştır
            assertEquals(expBody.getBody(), resMAP.getBody());
            // UserId değerini karşılaştır
            assertEquals(expBody.getUserId(), resMAP.getUserId());
            // Id değerini karşılaştır
            assertEquals(expBody.getId(), resMAP.getId());


    // MANUEL TEST ADIMLARI - JSONPlaceholder PUT Request POJO Testi
            //
            //1. TEST ÖN HAZIRLIK
            //- Test senaryosunu anlama: JSONPlaceholder API'ye PUT request
            //- İnternet bağlantısı kontrolü
            //- API test aracı hazırlama (Postman, Insomnia, vs.)
            //- Endpoint not et: https://jsonplaceholder.typicode.com/posts/70
            //
            //2. REQUEST BODY HAZIRLAMA
            //- Aşağıdaki JSON formatında request body oluştur:
            //{
            //    "title": "Ahmet",
            //    "body": "Merhaba",
            //    "userId": 10,
            //    "id": 70
            //}
            //
            //3. HEADER KONFİGÜRASYONU
            //- Content-Type: application/json
            //- Accept: application/json
            //
            //4. API İSTEĞİ GÖNDERME
            //- HTTP Method: PUT
            //- URL: https://jsonplaceholder.typicode.com/posts/70
            //- Body: Hazırlanan JSON'u ekle
            //- "Send" butonuna tıkla
            //
            //5. RESPONSE DOĞRULAMA ADIMLARI
            //
            //5.1 STATUS CODE VE HEADER KONTROLLERİ
            //- Response Status Code: 200 olmalı
            //- Content-Type: "application/json; charset=utf-8" olmalı
            //- Connection: "keep-alive" olmalı
            //
            //5.2 RESPONSE BODY YAPISAL KONTROL
            //- Response'un aşağıdaki structure'a sahip olduğunu kontrol et:
            //{
            //    "title": "Ahmet",
            //    "body": "Merhaba",
            //    "userId": 10,
            //    "id": 70
            //}
            //
            //5.3 DETAYLI ALAN KONTROLLERİ
            //
            //5.3.1 TEMEL ALANLAR
            //- title: "Ahmet" değerini kontrol et
            //- body: "Merhaba" değerini kontrol et
            //- userId: 10 değerini kontrol et
            //- id: 70 değerini kontrol et
            //
            //5.4 VERİ TİPİ KONTROLLERİ
            //- title: String tipinde olmalı
            //- body: String tipinde olmalı
            //- userId: Number (integer) tipinde olmalı
            //- id: Number (integer) tipinde olmalı
            //
            //6. POJO MAPPING TESTİ (GELİŞMİŞ KONTROL)
            //
            //6.1 JAVA OBJE DÖNÜŞÜMÜ
            //- JSON response'un Java objesine düzgün map edildiğini kontrol et
            //- Tüm alanların doğru veri tipleriyle eşleştiğini doğrula
            //
            //6.2 GETTER/SETTER TESTİ
            //- title alanı için getTitle() methodu çalışmalı
            //- body alanı için getBody() methodu çalışmalı
            //- userId alanı için getUserId() methodu çalışmalı
            //- id alanı için getId() methodu çalışmalı
            //
            //7. NEGATİF TEST SENARYOLARI (OPSİYONEL)
            //
            //7.1 GEÇERSİZ ENDPOINT
            //- Yanlış URL: https://jsonplaceholder.typicode.com/post/70
            //- Beklenen: 404 Not Found
            //
            //7.2 GEÇERSİZ JSON FORMATI
            //- Eksik alanlar içeren JSON gönder
            //- Beklenen: 400 Bad Request
            //
            //7.3 YANLIŞ VERİ TİPLERİ
            //- String yerine number: "title": 123
            //- Number yerine string: "userId": "on"
            //- Beklenen: 400 Bad Request
            //
            //8. PERFORMANS TESTİ (OPSİYONEL)
            //
            //8.1 RESPONSE SÜRESİ
            //- Response süresi 2 saniyeden az olmalı
            //- Connection başlangıç süresi kontrolü
            //
            //8.2 YÜK TESTİ
            //- Aynı anda multiple PUT request
            //- Sistemin handle edebildiğini kontrol et
            //
            //9. TEST DOKÜMANTASYONU
            //
            //9.1 KAYIT ALTINA ALINACAKLAR
            //- Request URL ve body
            //- Request headers
            //- Response status code
            //- Response headers
            //- Response body tam içeriği
            //- Response süresi
            //- Test tarih ve saati
            //
            //9.2 BEKLENEN DAVRANIŞLAR
            //- Status code her zaman 200 olmalı
            //- Tüm header'lar belirtilen değerlerde olmalı
            //- Response body request body ile tamamen aynı olmalı
            //- Veri tipleri korunmalı
            //
            //10. GÜVENLİK TESTLERİ (OPSİYONEL)
            //
            //10.1 SQL INJECTION
            //- "title": "Ahmet'; DROP TABLE users;--"
            //- Beklenen: Sanitize edilmiş response
            //
            //10.2 XSS TESTİ
            //- "body": "<script>alert('xss')</script>"
            //- Beklenen: Sanitize edilmiş response
            //
            //11. TEST KRİTERLERİ DEĞERLENDİRMESİ
            //
            //✅ BAŞARILI KRİTERLER:
            //- Status code 200
            //- Content-Type: "application/json; charset=utf-8"
            //- Connection: "keep-alive"
            //- Tüm alanlar expected değerlerle eşleşmeli
            //- Veri tipleri doğru olmalı
            //
            //❌ BAŞARISIZ KRİTERLER:
            //- Status code 200 değil
            //- Header değerleri uyuşmuyor
            //- Herhangi bir alanda değer uyuşmazlığı
            //- JSON parse hatası
            //
            //12. POJO ÖZEL KONTROLLERİ
            //
            //12.1 SERIALIZATION
            //- Java objesinin JSON'a düzgün dönüştüğünü kontrol et
            //
            //12.2 DE-SERIALIZATION
            //- JSON'ın Java objesine düzgün dönüştüğünü kontrol et
            //
            //12.3 CONSTRUCTOR TEST
            //- Parametreli constructor doğru çalışmalı
            //- Parametresiz constructor doğru çalışmalı
            //
            //NOT: Bu test JSONPlaceholder'ın mock API'sini kullanmaktadır.
            //Gerçek veritabanı değişikliği yapmaz, mock response döner.
        }
    }


