package com.montanha.factory.viagens;

import java.io.FileInputStream;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.montanha.Pojo.Viagem;

public class ViagemDataFactory {
    

    public static Viagem criarViagemDataBind() throws IOException {

       ObjectMapper objectMapper = new ObjectMapper();
       Viagem viagem = objectMapper.readValue(new FileInputStream("src/test/resources/requestBody/postV1Viagens.json"), Viagem.class);
       return viagem;
    }

    public static Viagem criarViagem() throws IOException{
        
        Viagem viagemUm = criarViagemDataBind();
        return viagemUm;
    }

    public static Viagem criarViagemSemLocalDestino() throws IOException{
        Viagem viagemSemDestino = criarViagemDataBind();
        viagemSemDestino.setLocalDeDestino("");
        return viagemSemDestino;
    }
    
    public static Viagem criarViagemSemDataPartida() throws IOException{
        Viagem viagemSemDataPartida = criarViagemDataBind();
        viagemSemDataPartida.setDataPartida("");
        return viagemSemDataPartida;
    }

    public static Viagem criarViagemSemAcompanhante() throws IOException{
        Viagem viagemSemAcompanhante = criarViagemDataBind();
        viagemSemAcompanhante.setAcompanhante("");
        return viagemSemAcompanhante;
    }
   
}
