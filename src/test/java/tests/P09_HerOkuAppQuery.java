package tests;

import baseUrl.BaseUrlHerOkuApp;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

// Bu sınıf, testler için temel URL ve spec nesnesini sağlayan
// BaseUrlHerOkuApp sınıfından kalıtım (extends) alır.

// Bu sayede, o sınıfta tanımlanan ayarlar:
// (base URL gibi) ve nesneler (specRestfull) bu sınıfta doğrudan kullanılabilir.

public class P09_HerOkuAppQuery extends BaseUrlHerOkuApp {
    /*
        TEST SENARYOSU ve ÇALIŞMA ADIMLARI:

        Bu testin amacı, belirli bir isme ve soyisme sahip bir rezervasyonun sistemde olup olmadığını kontrol etmektir.

        ÇALIŞMA AKIŞI:
        ----------------
        0. KALITIM ve HAZIRLIK (Test Metodu Çalışmadan Önce):
           - P09_HerOkuAppQuery sınıfı, BaseUrlHerOkuApp sınıfını 'extends' ettiği için, BaseUrlHerOkuApp içindeki @BeforeEach anotasyonuna sahip setup() metodu otomatik olarak çalıştırılır.
           - Bu setup() metodu, 'specRestfull' isimli RequestSpecification nesnesini oluşturur ve base URL'i (https://restful-booker.herokuapp.com) set eder.
           - Böylece, bu sınıftaki test metodu başladığında 'specRestfull' nesnesi hazır ve temel URL'i ayarlanmış durumdadır.

        1. URL ve QUERY PARAMETRELERİNİ AYARLAMA (Test Metodu İçinde):
           - Hazır olan 'specRestfull' nesnesine bu teste özel path parametresi ('/booking') ve query parametreleri (firstname="Josh", lastname="Allen") eklenir.
           - Sonuç olarak RestAssured'un hedef alacağı tam URL şu hale gelir: https://restful-booker.herokuapp.com/booking?firstname=Josh&lastname=Allen

        2. API'YE REQUEST GÖNDERME:
           - given().spec(specRestfull) komutu ile tüm bu ayarları (Base URL + Path + Query Params) içeren bir istek hazırlanır.
           - .when().get("/{pp1}") ile bu hedefe bir GET isteği gönderilir.
           - API'den dönen tüm yanıt, 'response' isimli bir değişkende saklanır.

        3. YANITI DOĞRULAMA (ASSERTION):
           - Dönen 'response' üzerinde testler yapılır.
           - Status code'un 200 (Başarılı) olduğu kontrol edilir.
           - Dönen JSON listesinin boyutunun 1'den büyük veya eşit olduğu (yani aranan isme sahip en az bir kayıt bulunduğu) doğrulanır.
     */
    @Test
    public void test(){
        // Adım 1: URL ve Query Parametrelerini Ayarla
        // Temel URL ve specRestfull nesnesi, kalıtım yoluyla gelen @BeforeEach metodu sayesinde zaten hazırlandı.
        // Şimdi sadece bu teste özel olan path ve query parametrelerini o nesneye ekliyoruz.
        specRestfull.pathParam("pp1","booking").queryParams("firstname","Josh","lastname","Allen");

        // Adım 2: API'ye GET Request'i Gönder ve Yanıtı Kaydet
        // given() ile başlayan komut, hazırlanan spec (base URL + path + query params) ile isteği oluşturur.
        Response response=given().spec(specRestfull).when().get("/{pp1}");

        // Adım 3: Dönen Yanıtı (Response) Doğrula
        // .then().assertThat() ile response üzerinde testler yapılır.
        response.then().assertThat().statusCode(200).body("size()", Matchers.greaterThanOrEqualTo(1));
    }
}