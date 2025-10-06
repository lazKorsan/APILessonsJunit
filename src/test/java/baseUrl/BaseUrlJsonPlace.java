package baseUrl;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;

public class BaseUrlJsonPlace {

    protected RequestSpecification specJsonPlace;
    @BeforeEach
    public void setup(){
        specJsonPlace=new RequestSpecBuilder().setBaseUri("https://jsonplaceholder.typicode.com").build();

    }
}