package tests;

import baseUrl.BaseUrlHerOkuApp;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import testDatas.TestDatasHerOkuApp;
import utils.DesktopUtils; // Güncellenmiş DesktopUtils sınıfımızı import ediyoruz

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P12_POST_HerOkuAppTestData extends BaseUrlHerOkuApp {
    /*
        TEST SENARYOSU ve ÇALIŞMA ADIMLARI:

        Bu test, bir API testinin adımlarını ve sonucunu, modern, renkli ve büyük
        masaüstü bildirimleri (pop-up) ile görselleştirir. Konsola herhangi bir çıktı üretmez.

        ÇALIŞMA AKIŞI:
        ----------------
        1. BAŞLANGIÇ BİLDİRİMİ:
           - DesktopUtils.showStartPopup() metodu çağrılır ve ekranda şık bir "Test Başlıyor" penceresi belirir.

        2. API TESTİ - HAZIRLIK:
           - Gerekli URL ve test verileri (request/expected body) hazırlanır.

        3. API TESTİ - REQUEST GÖNDERME:
           - DesktopUtils.showProgressPopup() ile "İşlemde..." bildirimi gösterilir.
           - API'ye POST isteği gönderilir.

        4. API TESTİ - DOĞRULAMA (ASSERTION):
           - Dönen yanıtın içeriği, beklenen veri ile karşılaştırılır.

        5. BAŞARI BİLDİRİMİ:
           - Tüm doğrulamalar başarılı ise, DesktopUtils.showSuccessPopup() çağrılır ve ekranda
             göz alıcı bir "Başarılı" penceresi belirir.
     */
    @Test
    public void test2() {
        // Adım 1: Başlangıç Pop-up'ını Göster
        DesktopUtils.showStartPopup("HerokuApp POST Test", "Yeni rezervasyon oluşturma ve doğrulama");

        // Adım 2.1: API URL'ini Tamamla
        specRestfull.pathParam("pp1", "booking");

        // Adım 2.2: Request ve Expected Body'leri Hazırla
        JSONObject reqBody = TestDatasHerOkuApp.reqBodyOlustur();
        JSONObject expBody = TestDatasHerOkuApp.expBodyOlustur();

        // Adım 3: API'ye Request Gönder
        DesktopUtils.showProgressPopup("API İsteği", "Rezervasyon oluşturma isteği gönderiliyor...");
        Response response = given()
                .spec(specRestfull)
                .contentType(ContentType.JSON)
                .when()
                .body(reqBody.toString())
                .post("/{pp1}");

        // Adım 4: Yanıtı Doğrula (Assertion)
        JsonPath resJP = response.jsonPath();

        assertEquals(expBody.getJSONObject("booking").get("firstname"), resJP.get("booking.firstname"));
        assertEquals(expBody.getJSONObject("booking").get("lastname"), resJP.get("booking.lastname"));
        assertEquals(expBody.getJSONObject("booking").get("totalprice"), resJP.get("booking.totalprice"));
        assertEquals(expBody.getJSONObject("booking").get("depositpaid"), resJP.get("booking.depositpaid"));
        assertEquals(expBody.getJSONObject("booking").get("additionalneeds"), resJP.get("booking.additionalneeds"));
        assertEquals(expBody.getJSONObject("booking").getJSONObject("bookingdates").get("checkin"),
                resJP.get("booking.bookingdates.checkin"));
        assertEquals(expBody.getJSONObject("booking").getJSONObject("bookingdates").get("checkout"),
                resJP.get("booking.bookingdates.checkout"));

        // Adım 5: Test Başarılı Pop-up'ını Göster
        DesktopUtils.showSuccessPopup("HerokuApp POST Test");

        // Son pencerenin ekranda kalma süresinin bitmesini beklemek için duraklatma.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Konsolu temiz tutmak için kasıtlı olarak boş bırakıldı.
        }
    }
}
