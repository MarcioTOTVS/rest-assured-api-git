import io.restassured.RestAssured;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class BookingTest {

    public String lerJson(String caminhoArquivo) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
    }

    // Define um método de teste
    @Test
    public void buscarReservas() {
        // Configura a URL base para as requisições da API
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Configura e executa a requisição GET para o endpoint "/booking/"
        given() // Define as configurações da requisição (headers, parâmetros, etc.)
                .header("Accept", "*/*") //adiciona o header accept
                .when() // Indica o início da execução da requisição
                .get("/booking/") // Especifica o endpoint a ser chamado
                .then() // Define as validações da resposta
                .statusCode(200) // Verifica se o status code da resposta é 200 (OK)
                .log().all();    // Loga no console todos os detalhes da resposta (body, headers, etc.)
    }

    @Test
    public void buscarReservasId() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        given()
                .header("Accept", "application/json")
                .when()
                .get("/booking/2084")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Marcio"))
                .body("lastname", equalTo("Silva"))
                .body("totalprice", equalTo(3))
                .body("depositpaid", is(true))
                .body("bookingdates.checkin", equalTo("2025-09-25"))
                .body("bookingdates.checkout", equalTo("2025-09-30"))
                .body("additionalneeds", equalTo("ACT D"))
                .log().all();    // Loga no console todos os detalhes da resposta (body, headers, etc.)

    }
    @Tag("Cadastro")
    @Test
    public void cadastrarReserva() throws IOException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        String jsonBody = lerJson("src/test/resources/payloads/reserva.json");

    given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .body("booking.firstname", equalTo("Marcio"))
            .body("booking.lastname", equalTo("Silva"))
            .body("booking.totalprice", equalTo(3))
            .body("booking.depositpaid", is(true))
            .body("booking.bookingdates.checkin", equalTo("2025-09-25"))
            .body("booking.bookingdates.checkout", equalTo("2025-09-30"))
            .body("booking.additionalneeds", equalTo("ACT D"));
    }
}
