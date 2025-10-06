package tests;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class P06_NasaApi {

    @Test
    public void TC_01(){

        // 1. Adım: Sorgulanacak API'nin endpoint URL'i bir String'e atanır.
        // Postman'de manuel olarak test edilen sorgunun endpoint'i:
        // https://api.nasa.gov/planetary/apod?api_key=3l1aFXgxGFtrwMYHEa6ohTPNAcdPLMWxGPYZrFBg&start_date=2024-04-01&end_date=2024-04-01
        String url = "https://api.nasa.gov/planetary/apod";

        // 2. Adım: API'ye gönderilecek sorgu parametreleri bir JSONObject olarak oluşturulur.
        // GET request'lerinde parametreler genellikle URL'e eklenir.
        // RestAssured bu işlemi .params() metodu ile kolaylaştırır.
        JSONObject reqBody = new JSONObject();
        reqBody.put("api_key","3l1aFXgxGFtrwMYHEa6ohTPNAcdPLMWxGPYZrFBg");
        // NASA API'si gelecekteki tarihler için bir görsel döndürmeyeceğinden,
        // testin başarılı olması için geçmiş bir tarih kullanılmıştır.
        reqBody.put("start_date","2024-04-01");
        reqBody.put("end_date","2024-04-01");

        // Hazırlanan request body'sini konsola yazdırarak doğruluğunu kontrol edebilirsiniz.
        System.out.println("Gönderilen Sorgu Parametreleri:\n" + reqBody);


        // 3. Adım: RestAssured ile API'ye GET isteği gönderilir.
        // given() ile isteğin ön koşulları (parametreler, header'lar vb.) belirlenir.
        // .params() metodu, JSONObject'teki anahtar-değer çiftlerini URL'e sorgu parametresi olarak ekler.
        // when() ile isteğin türü (GET, POST, vb.) ve hedef URL'i belirtilir.
        // Dönen yanıt 'Response' türünde bir objeye atanır.
        Response response = given()
                .params(reqBody.toMap()) // reqBody JSONObject'i Map'e çevrilerek parametre olarak ekleniyor.
                .when()
                .get(url);

        // 4. Adım: API'den dönen yanıtın içeriği .prettyPrint() metodu ile okunabilir formatta konsola yazdırılır.
        System.out.println("API'den Gelen Yanıt:");
        response.prettyPrint();

        // 5. Adım: Yanıt içerisinden istenen bilginin (resim url'i) ayıklanması.
        // Dönen yanıt bir JSON Array'i olduğu için ilk elemana '[0]' ile erişilir.
        // Sonrasında objenin içindeki 'url' anahtarının değeri .path() metodu ile alınır.
        String imageUrl = response.path("[0].url");
        System.out.println("Ayıklanan Resim URL'i: " + imageUrl);


    }
}
