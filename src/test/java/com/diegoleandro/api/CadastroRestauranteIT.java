package com.diegoleandro.api;

import com.diegoleandro.domain.model.Cozinha;
import com.diegoleandro.domain.model.Restaurante;
import com.diegoleandro.domain.repository.CozinhaRepository;
import com.diegoleandro.domain.repository.RestauranteRepository;
import com.diegoleandro.util.DatabaseCleaner;
import com.diegoleandro.util.ResourceUtils;
import  io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

    public static final String DADOS_INVALIDOS_PROBLEM_TITLE = "dados inválidos";
    public static final String VIOLAÇÃO_DE_NEGÓCIO_PROBLEM_TYPE = "Violação de regra de negócio";
    public static final int RESTAURANTE_ID_INEXISTENTE = 100;
    @LocalServerPort
    private int port;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private String jsonRestauranteCorreto;
    private String jsonRestauranteSemFrete;
    private String jsonRestauranteSemCozinha;
    private String jsonRestauranteComCozinhaInexistente;

    private Restaurante burgerTopRestaurante;


    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath ="/restaurantes";

        jsonRestauranteCorreto = ResourceUtils.getContentFromResource("/test-json/correto/restaurante-new-york-barbecue.json");
        jsonRestauranteSemFrete = ResourceUtils.getContentFromResource("/test-json/incorreto/restaurante-new-york-barbecue-sem-frete.json");
        jsonRestauranteSemCozinha = ResourceUtils.getContentFromResource("/test-json/incorreto/restaurante-new-york-barbecue-sem-cozinha.json");
        jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource("/test-json/incorreto/restaurante-new-york-barbecue-com-cozinha-inexistente.json");

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
             given()
                .accept(ContentType.JSON)
             .when()
                .get()
             .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
             given()
                .body(jsonRestauranteCorreto)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
             .when()
                 .post()
             .then()
                 .statusCode(HttpStatus.CREATED.value());

    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarSemTaxaFrete() {
            given()
                .body(jsonRestauranteSemFrete)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
            given()
               .body(jsonRestauranteComCozinhaInexistente)
               .contentType(ContentType.JSON)
               .accept(ContentType.JSON)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.equalTo(VIOLAÇÃO_DE_NEGÓCIO_PROBLEM_TYPE));
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
            given()
                .pathParam("restauranteId", burgerTopRestaurante.getId())
                .accept(ContentType.JSON)
            .when()
                 .get("/{restauranteId}")
            .then()
                 .statusCode(HttpStatus.OK.value())
                 .body("nome", Matchers.equalTo(burgerTopRestaurante.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
             given()
                .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
                .accept(ContentType.JSON)
             .when()
                .get("/{restauranteId}")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }






    private void prepararDados() {
        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        Cozinha cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);

        burgerTopRestaurante = new Restaurante();
        burgerTopRestaurante.setNome("Burger Top");
        burgerTopRestaurante.setTaxaFrete(new BigDecimal(10));
        burgerTopRestaurante.setCozinha(cozinhaAmericana);
        restauranteRepository.save(burgerTopRestaurante);

        Restaurante comidaMineiraRestaurante = new Restaurante();
        comidaMineiraRestaurante.setNome("Comida Mineira");
        comidaMineiraRestaurante.setTaxaFrete(new BigDecimal(10));
        comidaMineiraRestaurante.setCozinha(cozinhaBrasileira);
        restauranteRepository.save(comidaMineiraRestaurante);
    }



}
