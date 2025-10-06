package tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleSearchExample2 {

    // Sizin API bilgileriniz
    private static final String API_KEY = "b7b2fa14389757ea15bc2258534826b34b04f925cbde80e823237c7c70694143";
    private static final String SEARCH_ENGINE_ID = "92d19d034826a4f2f";

    public static void main(String[] args) {
        // Önce API konfigürasyonunu test edelim
        testApiConfiguration();

        // Sonra arama yapalım
        String query = "Java API documentation PDF";
        System.out.println("\n=== GOOGLE ARAMA SONUÇLARI ===");
        System.out.println("Arama Sorgusu: " + query);

        try {
            String result = performGoogleSearch(query);
            System.out.println("API Yanıtı:");
            System.out.println(result);

            // JSON yanıtını parse edip düzenli gösterelim
            parseAndPrintResults(result);

        } catch (Exception e) {
            System.err.println("Hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testApiConfiguration() {
        System.out.println("=== API KONFİGÜRASYON KONTROLÜ ===");
        System.out.println("API Key: " + (API_KEY != null ? API_KEY.substring(0, 10) + "..." : "NULL"));
        System.out.println("Search Engine ID: " + SEARCH_ENGINE_ID);

        // API Key format kontrolü
        if (API_KEY == null || API_KEY.equals("YOUR_API_KEY_HERE") || API_KEY.trim().isEmpty()) {
            System.out.println("❌ API Key ayarlanmamış!");
            return;
        }

        // Search Engine ID format kontrolü
        if (SEARCH_ENGINE_ID == null || SEARCH_ENGINE_ID.equals("YOUR_SEARCH_ENGINE_ID_HERE") || SEARCH_ENGINE_ID.trim().isEmpty()) {
            System.out.println("❌ Search Engine ID ayarlanmamış!");
            return;
        }

        System.out.println("✓ API bilgileri ayarlanmış");
    }

    public static String performGoogleSearch(String query) throws IOException {
        // Sorguyu URL encode et
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        // API URL'ini oluştur
        String urlString = String.format(
                "https://www.googleapis.com/customsearch/v1?key=%s&cx=%s&q=%s&num=5",
                API_KEY, SEARCH_ENGINE_ID, encodedQuery
        );

        System.out.println("\nİstek URL: " + urlString);

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Timeout ayarları
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);

        int responseCode = connection.getResponseCode();
        System.out.println("HTTP Yanıt Kodu: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Başarılı yanıtı oku
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();

        } else {
            // Hata durumunda detaylı bilgi al
            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(connection.getErrorStream()));
            String errorLine;
            StringBuilder errorResponse = new StringBuilder();

            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine);
            }
            errorReader.close();

            throw new IOException("HTTP hatası: " + responseCode +
                    "\nHata detayı: " + errorResponse.toString());
        }
    }

    // JSON yanıtını parse etmek için basit bir metod
    public static void parseAndPrintResults(String jsonResponse) {
        System.out.println("\n=== AYRINTILI SONUÇLAR ===");

        if (jsonResponse.contains("\"error\"")) {
            System.out.println("API'den hata döndü:");
            System.out.println(jsonResponse);
            return;
        }

        if (!jsonResponse.contains("\"items\"")) {
            System.out.println("Sonuç bulunamadı.");
            System.out.println("API Yanıtı: " + jsonResponse);
            return;
        }

        try {
            // Basit JSON parsing - gerçek uygulamada Gson/Jackson kullanın
            String[] items = jsonResponse.split("\"items\"")[1].split("\\},\\{");

            System.out.println("Toplam " + items.length + " sonuç bulundu:\n");

            for (int i = 0; i < Math.min(items.length, 5); i++) {
                String item = items[i];
                System.out.println("🔍 SONUÇ " + (i + 1) + ":");

                // Başlık çıkar
                if (item.contains("\"title\"")) {
                    String title = extractJsonValue(item, "title");
                    System.out.println("   Başlık: " + title);
                }

                // Link çıkar
                if (item.contains("\"link\"")) {
                    String link = extractJsonValue(item, "link");
                    System.out.println("   Link: " + link);
                }

                // Snippet çıkar
                if (item.contains("\"snippet\"")) {
                    String snippet = extractJsonValue(item, "snippet");
                    System.out.println("   Açıklama: " + snippet);
                }

                System.out.println("   " + "-".repeat(50));
            }

        } catch (Exception e) {
            System.out.println("Sonuçları parse ederken hata: " + e.getMessage());
            System.out.println("Ham JSON yanıtı: " + jsonResponse);
        }
    }

    // JSON'dan değer çıkarmak için yardımcı metod
    private static String extractJsonValue(String json, String key) {
        try {
            String keyPattern = "\"" + key + "\":\"";
            int startIndex = json.indexOf(keyPattern) + keyPattern.length();
            int endIndex = json.indexOf("\"", startIndex);
            return json.substring(startIndex, endIndex);
        } catch (Exception e) {
            return "Bulunamadı";
        }
    }

    // Alternatif basit sorgu metodu
    public static void simpleSearch(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String urlString = String.format(
                    "https://www.googleapis.com/customsearch/v1?key=%s&cx=%s&q=%s",
                    API_KEY, SEARCH_ENGINE_ID, encodedQuery
            );

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("Başarılı Yanıt:");
                System.out.println(response.toString());
            } else {
                System.out.println("Hata! Response Code: " + responseCode);
            }

        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }
}