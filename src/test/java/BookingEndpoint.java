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

    public void buscarReservasId(int reservaId, String firstname, String lastname, int totalprice, boolean depositpaid, String checkin, String checkout, String additionalneeds) {
        RestAssured.baseURI = BASE_URL;
        given()
                .header("Accept", "application/json")
                .when()
                .get("/booking/" + reservaId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo(firstname))
                .body("lastname", equalTo(lastname))
                .body("totalprice", equalTo(totalprice))
                .body("depositpaid", is(depositpaid))
                .body("bookingdates.checkin", equalTo(checkin))
                .body("bookingdates.checkout", equalTo(checkout))
                .body("additionalneeds", equalTo(additionalneeds))
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
}
