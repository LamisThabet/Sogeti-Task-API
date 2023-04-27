package tests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import com.sogeti.utilities.DataProviderClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class secondCaseTest {
    @BeforeMethod
    public static void setup() {
        RestAssured.baseURI = "http://api.zippopotam.us";
    }
    @Test(dataProvider = "tc2-data-provider",dataProviderClass = DataProviderClass.class)
    public void secondTestCase (String country,String postal_code,String place_name) {
        SoftAssert softAssert = new SoftAssert();
        Response response = given()
                .pathParam("country",country)
                .pathParam("postal-code",postal_code)
                .when()
                .get(baseURI+"/{country}/{postal-code}")
                .then()
                .log().all()
                .extract().response();


        // #1-Verify that the response status code is 200 and content type is JSON. //
        softAssert.assertEquals( response.statusCode(),200);
        String contentType = response.header("Content-Type");
        softAssert.assertEquals(contentType,"application/json","ContentType");

        // #2-Verify that the response time is below 1s. //
        ValidatableResponse valRes = response.then();
        long responseTimeInSeconds = response.getTimeIn(TimeUnit.SECONDS);
        System.out.println("Response time in seconds using getTimeIn():"+responseTimeInSeconds);
        //valRes.time(Matchers.lessThan(2L), TimeUnit.SECONDS);
        softAssert.assertTrue(responseTimeInSeconds<2,"check time less than 1 seconds");

        // #3-Verify in Response - "Place Name" for each input "Country" and "Postal Code". //
        String actual_place_name= response.jsonPath().get("places[0].'place name'");
        System.out.println("actual_place_name: " + actual_place_name);
        softAssert.assertEquals(actual_place_name,place_name);
        softAssert.assertAll();

    }
}
