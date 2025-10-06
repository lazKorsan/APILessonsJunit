package testDatas;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestDatasHerOkuApp {

    public static String title = null;
    public static String firstname = "firstname";
    public static String lastname = "lastname";
    public static String totalprice = "totalprice";
    public static String depositpaid = "depositpaid";
    public static String bookingdates = "bookingdates";
    public static String additionalneeds = "additionalneeds";
    public static String checkin = "checkin";
    public static String checkout = "checkout";
    public static String bookingid = "bookingid";
    public static String booking = "booking";

    public static int basariliStatusCode=200;
 /*
    {
                       "firstname" : "Ahmet",
                       "lastname" : "Bulut",
                       "totalprice" : 500,
                       "depositpaid" : false,
                       "bookingdates" : {
                           "checkin" : "2021-06-01",
                           "checkout" : "2021-06-10"
                       },
                       "additionalneeds" : "wi-fi"
                   }
  */

    public static JSONObject reqBodyOlustur(){
        JSONObject innerData=new JSONObject();
        innerData.put("checkin" , "2021-06-01");
        innerData.put("checkout" , "2021-06-10");
        JSONObject reqBody=new JSONObject();
        reqBody.put("firstname", "Ahmet");
        reqBody.put( "lastname", "Bulut");
        reqBody.put("totalprice", 500);
        reqBody.put("depositpaid", false);
        reqBody.put("bookingdates",innerData);
        reqBody.put("additionalneeds", "wi-fi");

        return reqBody;

    }

    public static JSONObject expBodyOlustur(){
        JSONObject expBody=new JSONObject();
        expBody.put("bookingid",46); // * id herdefasında değişiyor dikkat et
        expBody.put("booking",reqBodyOlustur());
        return expBody;
    }

    /*
    {
                            "firstname" : "Ahmet",
                            "lastname" : “Bulut”,
                            "totalprice" : 500,
                            "depositpaid" : false,
                            "bookingdates" : {
                                     "checkin" : "2021-06-01",
                                     "checkout" : "2021-06-10"
                                              },
                            "additionalneeds" : "wi-fi"
                        }
     */
    /**
     * 🔹 METHOD 1: Booking dates Map verisi oluşturur
     * 📌 AMAÇ: İç içe nested JSON objesi oluşturmak için
     * 🎯 KULLANIM: bookingdates kısmını dinamik olarak hazırlar
     *
     * @param checkin - Giriş tarihi (String format: "YYYY-MM-DD")
     * @param checkout - Çıkış tarihi (String format: "YYYY-MM-DD")
     * @return Map<String, Object> - Booking dates verisi
     */
    public static Map<String,Object> bookingMAPolustur(String checkin, String checkout){
        // 🔸 Yeni HashMap oluştur - bookingdates objesi için
        Map<String,Object> bookingMapData=new HashMap<>();

        // 🔸 Tarih değerlerini Map'e ekle
        bookingMapData.put("checkin",checkin);    // "2025-05-01"
        bookingMapData.put("checkout",checkout);  // "2025-05-08"

        // 🔸 Oluşturulan Map'i döndür
        return bookingMapData;
        // 🎯 DÖNEN DEĞER: {"checkin": "2025-05-01", "checkout": "2025-05-08"}
    }

    /**
     * 🔹 METHOD 2: Ana booking request body Map'ini oluşturur
     * 📌 AMAÇ: POST request için tüm booking verisini hazırlamak
     * 🎯 KULLANIM: API'ye gönderilecek tüm veriyi tek methodla oluşturur
     *
     * @return Map<String, Object> - Tam booking request body
     */
    public static Map<String,Object> mapDataOlustur(){
        // 🔸 Ana HashMap oluştur - tüm booking verisi için
        Map<String,Object> mapData=new HashMap<>();

        // 🔸 Temel bilgileri ekle
        mapData.put("firstname","Murat");          // Müşteri adı
        mapData.put("lastname","Babayiğit");       // Müşteri soyadı
        mapData.put("totalprice",500.0);           // Toplam fiyat (Double)
        mapData.put("depositpaid",true);           // Depozito ödendi mi? (Boolean)

        // 🔸 İÇ İÇE MAP: bookingdates objesini oluştur ve ekle
        // 📞 bookingMAPolustur() methodunu çağırarak nested data oluştur
        mapData.put("bookingdates",bookingMAPolustur("2025-05-01","2025-05-08"));

        // 🔸 Ek ihtiyaçları ekle
        mapData.put("additionalneeds","wi-fi");    // Ekstra ihtiyaçlar

        // 🔸 Tamamlanmış Map'i döndür
        return mapData;
        // 🎯 DÖNEN DEĞER:
        // {
        //   "firstname": "Murat",
        //   "lastname": "Babayiğit",
        //   "totalprice": 500.0,
        //   "depositpaid": true,
        //   "bookingdates": {
        //       "checkin": "2025-05-01",
        //       "checkout": "2025-05-08"
        //   },
        //   "additionalneeds": "wi-fi"
        // }
    }

    public static Map<String,Object> expDataOlustur(){
        Map<String,Object> expData=new HashMap<>();
        expData.put("bookingid",25.0);
        expData.put("booking",mapDataOlustur());

        return expData;
    }
    
    // Aşağıdaki hata, bir API isteğinden JSON formatında bir yanıt beklenirken,
    // bunun yerine HTML formatında bir yanıt (genellikle bir hata sayfası) alındığında ortaya çıkar.
    // Bu durum, sunucu tarafında bir hata oluştuğunu veya isteğin yanlış bir adrese yapıldığını gösterebilir.
    // Hata, 'wisequarter.com' sitesindeki bir JavaScript kodundan kaynaklanmaktadır ve
    // JSON ayrıştırma hatası ('Unexpected token '<'') olarak belirtilmiştir,
    // çünkü '<' karakteri HTML'in başlangıcını işaret eder ve geçerli bir JSON başlangıcı değildir.
    // SyntaxError: Unexpected token '<', "<!DOCTYPE "... is not valid JSON
    //    at parse (<anonymous>)
    //    at https://online.wisequarter.com/lib/javascript.php/1750188745/lib/jquery/jquery-3.7.1.min.js:2:77349
    //    at l (https://online.wisequarter.com/lib/javascript.php/1750188745/lib/jquery/jquery-3.7.1.min.js:2:77466)
    //    at XMLHttpRequest.<anonymous> (https://online.wisequarter.com/lib/javascript.php/1750188745/lib/jquery/jquery-3.7.1.min.js:2:80265)
}
