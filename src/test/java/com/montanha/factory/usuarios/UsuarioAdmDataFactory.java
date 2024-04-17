package com.montanha.factory.usuarios;

import com.montanha.Pojo.Usuario;

public class UsuarioAdmDataFactory {

    public static Usuario criarUsuarioAdministrador(){
        
        Usuario usuarioAdm = new Usuario();

        usuarioAdm.setEmail("admin@email.com");
        usuarioAdm.setSenha("654321");

        return usuarioAdm;
    }

    public static Usuario criarUsuarioComum(){
        Usuario usuarioComum = new Usuario();

        usuarioComum.setEmail("usuario@email.com");
        usuarioComum.setSenha("123456");

        return usuarioComum;
    }
    
    public static Usuario criarUsuarioComErro(){

        Usuario usuarioComErro = new Usuario();

        usuarioComErro.setEmail("usu@email.com");
        usuarioComErro.setSenha("123654");

        return usuarioComErro;
    }
}
