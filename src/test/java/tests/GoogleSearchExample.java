package tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleSearchExample {

    // TODO: Bu alanları kendi Google API bilgilerinizle doldurun
    // 1. Google Cloud Console'dan alacağınız API anahtarı
    private static final String API_KEY = "a19edcd93784e8b329c38b31dc590526c012e3a07980f2522f8bfcd2af9be076";
    // 2. Programlanabilir Arama Motoru'ndan alacağınız Arama Motoru Kimliği (CX)
    private static final String SEARCH_ENGINE_ID = "92d19d034826a4f2f";

    //  API_KEY = a19edcd93784e8b329c38b31dc590526c012e3a07980f2522f8bfcd2af9be076

    // <script async src="https://cse.google.com/cse.js?cx=92d19d034826a4f2f">
    //</script>
    //<div class="gcse-search"></div>

    public static void main(String[] args) {
        String query = "Gemini AI";

        try {
            // Arama sorgusunu URL formatına uygun şekilde kodlayın
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

            // Google Custom Search JSON API'sine uygun URL'yi oluşturun
            String urlString = String.format(
                "https://www.googleapis.com/customsearch/v1?key=%s&cx=%s&q=%s",
                API_KEY, SEARCH_ENGINE_ID, encodedQuery
            );

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // HTTP 200 (OK) yanıtı geldiyse, gelen JSON verisini okuyun
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // JSON yanıtını ekrana yazdırın
                System.out.println("API Response:");
                System.out.println(response.toString());
            } else {
                System.out.println("API isteği başarısız oldu. Response Code: " + responseCode);
            }

        } catch (IOException e) {
            System.out.println("Bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
