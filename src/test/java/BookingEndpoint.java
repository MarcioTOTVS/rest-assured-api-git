import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class BookingEndpoint {

    private final String BASE_URL = "https://restful-booker.herokuapp.com";

    public void buscarReservas () {
        RestAssured.baseURI = BASE_URL;

        given()
                .header("Accept", "*/*")
                .when()
                .get("/booking/")
                .then()
                .statusCode(200)
                .log().all();
    }


    public int cadastrarReserva(String jsonBody) {
        RestAssured.baseURI = BASE_URL;
        return given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .extract()
                .path("bookingid");
    }

    public void buscarReservasId(int reservaId) {
        RestAssured.baseURI = BASE_URL;
        given()
                .header("Accept", "application/json")
                .when()
                .get("/booking/" + reservaId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Marcio"))
                .body("lastname", equalTo("Silva de Paula"))
                .body("totalprice", equalTo(3966))
                .body("depositpaid", is(true))
                .body("bookingdates.checkin", equalTo("2025-09-25"))
                .body("bookingdates.checkout", equalTo("2025-09-30"))
                .body("additionalneeds", equalTo("ACT D"))
                .log().all();
    }
}
