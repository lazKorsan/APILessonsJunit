package testDatas;

import BaseStrings.AllStrings;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestDatasJPH {

    // Testlerde sıkça kullanılan ve değişmeyen değerler, kolay erişim için static değişken olarak tanımlanır.
    public static int basariliStatusCode = 200;
    public static String contentType = "application/json; charset=utf-8";
    public static String hconnectionHeader = "keep-alive";

    /**
     * Bu metot, JSONPlaceholder API'sindeki /posts/22 endpoint'i için beklenen (expected) veriyi
     * bir JSONObject olarak oluşturur ve döndürür. Testlerde, API'den gelen gerçek (actual) yanıt ile
     * bu beklenen veri karşılaştırılır.
     *
     * ÇALIŞMA ADIMLARI:
     * 1. Boş bir JSONObject nesnesi oluşturulur.
     * 2. .put() metodu kullanılarak, beklenen verinin her bir anahtar-değer (key-value) çifti bu nesneye eklenir.
     * 3. Doldurulan JSONObject nesnesi, metodu çağıran yere döndürülür.
     *
     * @return Beklenen veriyi içeren bir JSONObject.
     */
    public static JSONObject jphExpDataOlustur(){
        JSONObject expData=new JSONObject();
        expData.put( "userId", 3);
        expData.put("id", 22);
        expData.put("title", "dolor sint quo a velit explicabo quia nam");
        expData.put("body","eos qui et ipsum ipsam suscipit aut\nsed omnis non odio" +
                "\nexpedita earum mollitia molestiae aut atque rem suscipit\nnam impedit esse");

        return expData;
    }

    /**
     * Bu metot, API'ye gönderilecek bir request body'sini veya API'den dönmesi beklenen bir yanıtı
     * Map<String, Object> formatında oluşturur. Map kullanmak, JSONObject'e göre daha esnektir ve
     * RestAssured tarafından otomatik olarak JSON'a çevrilir.
     *
     * ÇALIŞMA ADIMLARI:
     * 1. Boş bir HashMap nesnesi oluşturulur.
     * 2. .put() metodu kullanılarak, verinin her bir anahtar-değer çifti bu Map'e eklenir.
     *    (JSON'daki sayılar genellikle double olarak ele alınır, bu yüzden 10.0 ve 70.0 kullanılır.)
     * 3. Doldurulan Map nesnesi, metodu çağıran yere döndürülür.
     *
     * @return Test verisini içeren bir Map.
     */
    public static Map<String, Object> jphMapDataOlustur(){
        Map<String,Object> mapData = new HashMap<>();
        mapData.put("title","Ahmet");
        mapData.put("body","Merhaba");
        mapData.put("userId",10.0);
        mapData.put("id",70.0);
        return mapData;
    }

    /**
     * Bu metot, yukarıdaki jphMapDataOlustur() metodunun parametreli ve dinamik halidir.
     * Testin içinden gönderilen değerlere göre bir Map (JSON objesi) oluşturur. Bu, aynı metodu
     * farklı verilerle tekrar tekrar kullanabilmemizi sağlar, kod tekrarını önler.
     *
     * ÇALIŞMA ADIMLARI:
     * 1. Boş bir HashMap nesnesi oluşturulur.
     * 2. .put() metodu kullanılarak, metoda parametre olarak gelen değerler (title, body, userId, id)
     *    ilgili anahtarlarla Map'e eklenir.
     * 3. Doldurulan Map nesnesi, metodu çağıran yere döndürülür.
     *
     * @param title  Oluşturulacak post'un başlığı.
     * @param body   Oluşturulacak post'un içeriği.
     * @param userId Post'u oluşturan kullanıcının ID'si.
     * @param id     Post'un ID'si.
     * @return Verilen parametrelerle oluşturulmuş bir Map.
     */
    public static Map<String, Object> jphMapDataOlusturPli(String title, String body, double userId, double id){
        Map<String,Object> mapData = new HashMap<>();
        mapData.put("title",title);
        mapData.put("body",body);
        mapData.put("userId",userId);
        mapData.put("id",id);
        return mapData;
    }



    public static JSONObject reqBodyOlustur(){
        JSONObject innerData=new JSONObject();
        innerData.put(AllStrings.CHECKIN , "2021-06-01");
        innerData.put(AllStrings.CHECKOUT , "2021-06-10");

        JSONObject reqBody=new JSONObject();
        reqBody.put(AllStrings.FIRST_NAME, AllStrings.EXPECTED_FIRST_NAME);
        reqBody.put(AllStrings.LAST_NAME, AllStrings.EXPECTED_LAST_NAME);
        reqBody.put(AllStrings.TOTAL_PRICE, 500);
        reqBody.put(AllStrings.DEPOSIT_PAID, false);
        reqBody.put(AllStrings.BOOKING_DATES,innerData);
        reqBody.put(AllStrings.ADDITIONAL_NEEDS, "wi-fi");

        return reqBody;


    }

}