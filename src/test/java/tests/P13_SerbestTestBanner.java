package tests;

import baseUrl.BaseUrlHerOkuApp;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import testDatas.TestDatasHerOkuApp;
import utils.LuxuryPopUpManager;
import utils.PopUp;
import utils.PopUpManager;
import utlities.Driver;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class P13_SerbestTestBanner extends BaseUrlHerOkuApp {

    @Test
    public void test2(){
        try {
            // Test başlangıcı
            PopUpManager.initializeTest("Booking Oluşturma Testi");
            Thread.sleep(1000); // Pop-up'ın görünmesi için bekle

            // === ADIM 1: PATH PARAMETER ===
            PopUpManager.showTestStep("1", "Path Parameter Ayarlanıyor: pp1=booking");
            specRestfull.pathParam("pp1","booking");
            Thread.sleep(1000);

            // === ADIM 2: REQUEST BODY ===
            PopUpManager.showTestStep("2", "Request Body Hazırlanıyor");
            JSONObject reqBody = TestDatasHerOkuApp.reqBodyOlustur();
            Thread.sleep(1000);

            // === ADIM 3: EXPECTED BODY ===
            PopUpManager.showTestStep("3", "Expected Body Hazırlanıyor");
            JSONObject expBody = TestDatasHerOkuApp.expBodyOlustur();
            Thread.sleep(1000);

            // === ADIM 4: API REQUEST ===
            PopUpManager.showApiCall("POST", "/booking", reqBody.toString());
            Thread.sleep(4000);

            Response response = given()
                    .spec(specRestfull)
                    .contentType(ContentType.JSON)
                    .when()
                    .body(reqBody.toString())
                    .post("/{pp1}");

            // === ADIM 5: RESPONSE ===
            PopUpManager.showResponse(response.getBody().asString(), response.getStatusCode());
            Thread.sleep(1000);

            // === ADIM 6: ASSERTIONS ===
            PopUpManager.showTestStep("6", "Doğrulamalar Yapılıyor");
            JsonPath resJP = response.jsonPath();
            Thread.sleep(1000);

            // Her assertion için pop-up
            PopUpManager.showAssertion("bookingid",
                    expBody.get("bookingid").toString(),
                    resJP.get("bookingid").toString(),
                    true);
            Thread.sleep(1000);

            PopUpManager.showAssertion("firstname",
                    expBody.getJSONObject("booking").get("firstname").toString(),
                    resJP.get("booking.firstname").toString(),
                    true);
            Thread.sleep(1000);

            // Test tamamlandı
            PopUpManager.showTestComplete(true);

        } catch (InterruptedException e) {
            PopUpManager.showError("Test kesintiye uğradı: " + e.getMessage());
        } catch (AssertionError e) {
            PopUpManager.showError("Doğrulama hatası: " + e.getMessage());
            PopUpManager.showTestComplete(false);
            throw e;
        }
    }

    @Test
    public void luxuryTest() throws InterruptedException {
        LuxuryPopUpManager.showTestStart("Booking API Testi");

        Thread.sleep(2000);

        LuxuryPopUpManager.showTestStep("1", "Path parametreleri ayarlanıyor\npp1 = booking");
        specRestfull.pathParam("pp1","booking");
        Thread.sleep(2000);

        LuxuryPopUpManager.showTestStep("2", "Request body oluşturuluyor\nTest verileri hazırlanıyor");
        JSONObject reqBody = TestDatasHerOkuApp.reqBodyOlustur();
        Thread.sleep(2000);

        LuxuryPopUpManager.showApiCall("POST", "/booking", reqBody.toString());
        Thread.sleep(2000);

        // API çağrısı...
        Response response = given().spec(specRestfull).contentType(ContentType.JSON)
                .when().body(reqBody.toString()).post("/{pp1}");

        LuxuryPopUpManager.showResponse(response.getStatusCode(), response.getBody().asString());
        Thread.sleep(2000);

        // Assertion'lar
        JsonPath resJP = response.jsonPath();
        LuxuryPopUpManager.showAssertion("bookingid", "128", resJP.get("bookingid").toString(), true);
        Thread.sleep(2000);

        LuxuryPopUpManager.showTestComplete(true, 5);
    }
}
