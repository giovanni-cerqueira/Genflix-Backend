package com.genflix.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.genflix.model.Usuario;


/**
 * A anotação@TestMethodOrder(MethodOrderer.OrderAnnotation.class) habilita a opção
 * de forçar o Junit a executar todos os testes na ordem pré definida pela pessoa 
 * desenvolvedora através da anotação @Order(numero)
 */


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {
	
	/**
	 * 
	 *  O objeto do tipo TestRestTemplate será utilizado para enviar uma requisição http para
	 *  a API Rest, permitindo testar o funcionamento dos endpoints da classe UsuarioController
	 * 
	 */
  
    @Autowired
    private TestRestTemplate testRestTemplate;

    
    /**
	 * Alteração: Foram criados 3 objetos Usuario:
	 * 1) usuario (Testar o método post) 
	 * 2) usuarioUpdate (Testar o método put)
	 * 3) usuarioAdmin (Criar o usuário para logar na API)
	 * 
	 * Injeção da classe Usuario Repository
	 */

	private Usuario usuario;
	private Usuario usuarioUpdate;
	//private Usuario usuarioAdmin; (NÃO UTILIZADO)

    @BeforeAll
    public void start() throws ParseException {
    	
    	//LocalDate dataPost = LocalDate.parse("2000-07-22", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        //usuario = new Usuario(0L, "Administrador", "admin@email.com.br", "admin123", dataPost);
        
         
         LocalDate dataPut = LocalDate.parse("2002-07-22", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
         usuarioUpdate = new Usuario(1L, "Giovanni Cerqueira", "giovanni@email.com.br", "giovanni123", dataPut);
         //Não esquecer de verificar o ID no banco de dados. Neste caso, o banco estava vazio!
 	}

    @Disabled
    @Test
    @DisplayName("✔ Cadastrar Usuário!")
    public void deveRealizarPostUsuario() {
    	/**
		 * Cria uma requisição http utilizando o objeto usuario
		 * Semelhante ao que o Postman faz
		 */

        HttpEntity<Usuario> request = new HttpEntity<Usuario>(usuario);
        
        /**
		 * Envia a requisição http através do método exchange da classe TestRestTemplate
		 * 
		 * Parâmetros:
		 * 
		 * Endpoint: caminho do endpoint
		 * Método: Post
		 * Requisição: request
		 * Claase de retorno: Usuario.class (objeto é do tipo Usuario)
		 */
        
        ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, request, Usuario.class);
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        
        /**
		 * Verifica se a resposta da requisição (StatusCode) é igual a 201 (Created). 
		 * Em caso positivo, o teste passou!
		 */
    }
  
    @Disabled
    @Test
    @DisplayName("�? Listar todos os Usuários!")
    public void deveMostrarTodosUsuarios() {
    	/**
		 * Envia a requisição http através do método exchange da classe TestRestTemplate
		 * 
		 * Como o método utiliza o verbo Get, ele não tem requisição
		 * 
		 * Parâmetros:
		 * 
		 * Endpoint: caminho do endpoint
		 * Verbo: Get
		 * Requisição: null
		 * Classe de retorno: String.class (objeto de retorno será uma String - lista de usuários)
		 * 
		 * Como a nossa API está com a segurança habilitada e o método de consulta não está 
		 * liberado de autenticação é necessário passar um usuário e uma senha.
		 * O objeto TestRestTemplate possui o método withBasicAuth() que se encarrega de fazer o login
		 * e passar o token gerado para o método,
		 * O Usuário admin será criado no método start() caso ele não exista no banco de dados
		 * 
		 */
        ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("admin@email.com.br", "admin123").exchange("/usuarios/all", HttpMethod.GET, null, String.class);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        
        /**
		 * Verifica se a resposta da requisição (StatusCode) é igual a 200 (Ok). 
		 * Em caso positivo, o teste passou!
		 */

  }
    //@Disabled
    @Test
    @DisplayName("😳 Alterar Usuário!")
    public void deveRealizarPutUsuario() {
    	
    	/**
		 * Cria uma requisição http utilizando o objeto usuario
		 * Semelhante ao que o Postman faz
		 */
        
        HttpEntity<Usuario> request = new HttpEntity<Usuario>(usuarioUpdate);
        
        /**
		 * Envia a requisição http através do método exchange da classe TestRestTemplate
		 * 
		 * Parâmetros:
		 * 
		 * Endpoint: caminho do endpoint
		 * Verbo: Put
		 * Requisição: request
		 * Claase de retorno: Usuario.class (objeto é do tipo Usuario)
		 */
        
        ResponseEntity<Usuario> resposta = testRestTemplate.withBasicAuth("admin@email.com.br", "admin123").exchange("/usuarios/alterar", HttpMethod.PUT, request, Usuario.class);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        
        /**
		 * Verifica se a resposta da requisição (StatusCode) é igual a 200 (Ok). 
		 * Em caso positivo, o teste passou!
		 */

  }
    

}