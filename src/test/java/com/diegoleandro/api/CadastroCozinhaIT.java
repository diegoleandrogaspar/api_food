package com.diegoleandro.api;

import com.diegoleandro.domain.model.Cozinha;
import com.diegoleandro.domain.repository.CozinhaRepository;
import com.diegoleandro.util.DatabaseCleaner;
import com.diegoleandro.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

    public static final int COZINHA_ID_INEXISTENTE = 100;
    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;


    private Integer quantidadeCozinhas;
    private String jsonCorretoCozinhaChinesa;
    private Cozinha cozinhaAmericana;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";
        jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource("/test-json/correto/cozinha.json");

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
           given()
                .accept(ContentType.JSON)
             .when()
                .get()
             .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConterQuantidadeCozinhas_QuandoConsultarCozinhas() {
        quantidadeCozinhas = cozinhaRepository.findAll().size();

        given()
                .accept(ContentType.JSON)
          .when()
                .get()
          .then()
                .body("", Matchers.hasSize(quantidadeCozinhas));

    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha() {
        given()
           .body(jsonCorretoCozinhaChinesa)
           .contentType(ContentType.JSON)
           .accept(ContentType.JSON)
        .when()
           .post()
        .then()
           .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
        given()
              .pathParam("cozinhaId", cozinhaAmericana.getId())
              .accept(ContentType.JSON)
        .when()
             .get("/{cozinhaId}")
        .then()
             .statusCode(HttpStatus.OK.value())
             .body("nome", Matchers.equalTo(cozinhaAmericana.getNome()));

    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
        given()
            .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
             .get("/{cozinhaId}")
        .then()
             .statusCode(HttpStatus.NOT_FOUND.value());
    }


    private void prepararDados() {
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Tailandesa");
        cozinhaRepository.save(cozinha1);

        cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);
    }
}
