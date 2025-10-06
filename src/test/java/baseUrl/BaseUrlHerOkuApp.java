package baseUrl;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;

public class BaseUrlHerOkuApp {

    // Base URL'i herkesin erişebileceği, değiştirilemez bir sabit (constant) olarak tanımlıyoruz.
    // public: Diğer paketlerden erişilebilir.
    // static: Sınıf adıyla doğrudan erişilebilir (nesne oluşturmaya gerek yok).
    // final: Değeri değiştirilemez.
    public static final String BASE_URI = "https://restful-booker.herokuapp.com";

    protected RequestSpecification specRestfull;

    @BeforeEach
    public void setup(){
        // specRestfull nesnesini oluştururken artık bu sabiti kullanıyoruz.
        specRestfull = new RequestSpecBuilder().setBaseUri(BASE_URI).build();
    }
}
