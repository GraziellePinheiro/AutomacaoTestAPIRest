package com.montanha.Isolada;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.montanha.Pojo.Usuario;
import com.montanha.Pojo.Viagem;
import com.montanha.config.Configuracoes;
import com.montanha.factory.usuarios.UsuarioAdmDataFactory;
import com.montanha.factory.viagens.ViagemDataFactory;

import io.restassured.http.ContentType;


import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import java.io.IOException;

public class ViagensTest {
    private String token;
    private String tokenUsuarioComum;

    @Before
    public void setUp(){

        //Configuração test.properties e config

        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);

        baseURI = configuracoes.baseURI();
        port = configuracoes.port();
        basePath = configuracoes.basePath();

        Usuario usuarioAdm = UsuarioAdmDataFactory.criarUsuarioAdministrador();
        
        this.token = given()
            .contentType(ContentType.JSON)
            .body(usuarioAdm)
        .when()
            .post("/v1/auth")
        .then()
            .extract()
                .path("data.token");

        Usuario usuarioComum = UsuarioAdmDataFactory.criarUsuarioComum();

        this.tokenUsuarioComum = given()
            .contentType(ContentType.JSON)
            .body(usuarioComum)
        .when()
            .post("/v1/auth")
        .then()
            .extract()
                .path("data.token");
    }

    @Test
    public void testCadastroViagemValidaRetornaSucesso201() throws IOException{
        
        Viagem viagemUm = ViagemDataFactory.criarViagem();
        

        given()
            .contentType(ContentType.JSON)
            .body(viagemUm)
            .header("Authorization", this.token)
        .when()
            .post("/v1/viagens")
        .then()
            .assertThat()
                .statusCode(201)
                .body("data.localDeDestino", equalTo("Peru"))
                .body("data.acompanhante", equalToIgnoringCase("grazi"));

    }

    @Test
    public void testCadastroViagemSemLocalDeDestino400() throws IOException{
        
        Viagem viagemSemLocalDeDestino = ViagemDataFactory.criarViagemSemLocalDestino();

        given()
            .contentType(ContentType.JSON)
            .body(viagemSemLocalDeDestino)
            .header("Authorization", this.token)
        .when()
            .post("/v1/viagens")
        .then()
            .assertThat()
                .statusCode(400);
            
    }
    
    @Test
    public void testCadastroViagemSemDataDePartidaInsucesso400() throws IOException{
        
        Viagem viagemSemDataDePartida = ViagemDataFactory.criarViagemSemDataPartida();

        given()
            .contentType(ContentType.JSON)
            .body(viagemSemDataDePartida)
            .header("Authorization", this.token)
        .when()
            .post("/v1/viagens")
        .then()
            .assertThat()
                .statusCode(400)
                .body("error", equalTo("Bad Request"))
                .body("errors[0].defaultMessage", equalTo("Data da Partida é uma informação obrigatória"));
            
    }

    @Test
    public void testeCadastrarViagemSemAcompanhanteSucesso201() throws IOException{
        Viagem viagemSemAcompanhante = ViagemDataFactory.criarViagemSemAcompanhante();

        given()
            .contentType(ContentType.JSON)
            .body(viagemSemAcompanhante)
            .header("Authorization", token)
        .when()
            .post("/v1/viagens")
        .then()
            .assertThat()
                .statusCode(201);
    }

    // Testes de Aderencia com JsonSchema

    @Test
    public void testCadastroViagemComTestesDeAderenciaRetornaSucesso201() throws IOException{
        
        Viagem viagemUm = ViagemDataFactory.criarViagem();
        

        given()
            .contentType(ContentType.JSON)
            .body(viagemUm)
            .header("Authorization", this.token)
        .when()
            .post("/v1/viagens")
        .then()
            .assertThat()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/postV1ViagensViagemValida.json"));

    }

    @Test
    public void testRetornaUmaViagemPossuiStatusCode200MostraLocalDeDestino() throws IOException{
        given()
            .header("Authorization", tokenUsuarioComum)
        .when()
            .get("/v1/viagens/1")
        .then()
            .assertThat()
                .statusCode(200)
                .body("data.localDeDestino", equalTo("Sorocaba"));
    }

    @Test
    public void testRetornaUmaViagemPossuiStatusCode200ViaMockable() throws IOException{
        given()
            .header("Authorization", tokenUsuarioComum)
        .when()
            .get("/v1/viagens/1")
        .then()
            .assertThat()
                .statusCode(200)
                .body("data.temperatura", equalTo(30.00f));
    }
    

 
}


