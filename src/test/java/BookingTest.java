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

    BookingEndpoint bookingEndpoint = new BookingEndpoint();

    public String lerJson(String caminhoArquivo) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
    }

    // Define um método de teste para buscar reservas
    @Tag("Busca")
    @Test
    public void buscarReservas() {
        bookingEndpoint.buscarReservas();
    }

    @Tag("BuscaId")
    @Test
    public void buscarReservasId() {
        bookingEndpoint.buscarReservasId();
    }

    @Tag("Cadastro")
    @Test
    public void cadastrarReserva() throws IOException {
        String jsonBody = lerJson("src/test/resources/payloads/reserva.json");
        int reservaId = bookingEndpoint.cadastrarReserva(jsonBody);
    }
}
