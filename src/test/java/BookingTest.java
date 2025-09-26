import io.restassured.RestAssured;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Permite compartilhar estado entre métodos de teste
public class BookingTest {

    private int reservaId; // Variável para guardar o ID gerado

    public String lerJson(String caminhoArquivo) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
    }

    // Define um método de teste para buscar reservas
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
        // Usa o ID gerado no cadastro para buscar a reserva correta
        given()
                .header("Accept", "application/json")
                .when()
                .get("/booking/" + reservaId) // Busca pelo ID salvo
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Marcio"))
                .body("lastname", equalTo("Silva"))
                .body("totalprice", equalTo(3))
                .body("depositpaid", is(true))
                .body("bookingdates.checkin", equalTo("2025-09-25"))
                .body("bookingdates.checkout", equalTo("2025-09-30"))
                .body("additionalneeds", equalTo("ACT D"))
                .log().all();
    }
    @Tag("Cadastro")
    // Após cadastrar e extrair o ID, faça uma nova requisição para validar os dados
    @Test
    public void cadastrarReserva() throws IOException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        String jsonBody = lerJson("src/test/resources/payloads/reserva.json");

        // Cadastro e extração do ID
        reservaId = given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .extract()
                .path("bookingid");

        // Validação dos dados cadastrados
        given()
                .header("Accept", "application/json")
                .when()
                .get("/booking/" + reservaId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Marcio"))
                .body("lastname", equalTo("Silva"))
                .body("totalprice", equalTo(3))
                .body("depositpaid", is(true))
                .body("bookingdates.checkin", equalTo("2025-09-25"))
                .body("bookingdates.checkout", equalTo("2025-09-30"))
                .body("additionalneeds", equalTo("ACT D"))
                .log().all();
    }
}
