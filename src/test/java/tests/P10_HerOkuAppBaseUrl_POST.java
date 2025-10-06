package tests;

import baseUrl.BaseUrlHerOkuApp;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class P10_HerOkuAppBaseUrl_POST extends BaseUrlHerOkuApp {
    /*
        // ... (Önceki açıklamalar aynı kalacak)
     */
    @Test
    public void test(){
        // Adım 1.A: Hedef URL'i Ayarla
        specRestfull.pathParam("pp1","booking");

        // Adım 1.B: Request Body'yi (JSON) Oluştur
        JSONObject innerData=new JSONObject();
        innerData.put( "checkin" , "2025-06-01");
        innerData.put( "checkout" , "2025-06-10");

        JSONObject reqBody=new JSONObject();
        reqBody.put("firstname","Murat");
        reqBody.put("lastname","Yiğit");
        reqBody.put("totalprice",500);
        reqBody.put("depositpaid",true);
        reqBody.put("additionalneeds","wi-fi");
        reqBody.put("bookingdates",innerData);

        // Adım 2: API'ye POST Request'i Gönder ve Yanıtı Kaydet
        Response response=given()
                .contentType(ContentType.JSON)
                .spec(specRestfull) // <-- DİKKAT: Bütün temel bilgileri bu nesneden alacak
                .when()
                .body(reqBody.toString())
                .post("/{pp1}"); // <-- KRİTİK NOKTA BURASI

        /*
            KRİTİK NOKTA: .post("/{pp1}") Metodu Nereye Bakar?

            Bu satıra gelindiğinde, RestAssured isteği göndermek için bir "İNŞA (BUILD)" işlemi başlatır.
            .post() metodu, geriye dönüp o ana kadar zincire eklenmiş TÜM bilgileri toplar:

            1.  İLK OLARAK `.spec(specRestfull)`'e bakar. Bu ona şunu söyler:
                "Tüm temel ayarlarım için 'specRestfull' nesnesinin içine bakmalıyım."

            2.  'specRestfull' nesnesinin içine baktığında iki şey bulur:
                a) BaseUrlHerOkuApp sınıfından gelen TEMEL URL: "https://restful-booker.herokuapp.com"
                b) Bu testin başında eklediğimiz PATH PARAMETRESİ: pp1 = "booking"

            3.  Son olarak, .post() metodunun kendi içindeki path'i alır: "/{pp1}"

            4.  TÜM BUNLARI BİRLEŞTİRİR:
                Temel URL + .post() içindeki path'in çözümlenmiş hali
                "https://restful-booker.herokuapp.com" + "/booking"
                --> SONUÇ URL: "https://restful-booker.herokuapp.com/booking"

            Aynı anda, zincirdeki diğer bilgileri de ( .contentType() ve .body() ) pakete ekler ve
            bu nihai URL'e, bu nihai gövde (body) ile bir POST isteği gönderir.

            Yani .post(), tek başına bir anlam ifade etmez; kendisinden önce gelen tüm `given()` bloğunun
            bir özetidir ve isteği ateşleyen son komuttur.
         */

        response.prettyPrint();

        // Adım 3: Dönen Yanıtı Doğrula
        response.then().assertThat()
                .statusCode(200)
                .body("booking.firstname", Matchers.equalTo("Murat"));
    }
}