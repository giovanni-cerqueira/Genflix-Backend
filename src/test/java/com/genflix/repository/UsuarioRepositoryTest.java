package com.genflix.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.genflix.model.Usuario;
import com.genflix.repository.UsuarioRepository;

/**
 * Neste teste, além da anotação: @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT),
 * precisamos adicionar a anotação @TestInstance(TestInstance.Lifecycle.PER_CLASS)
 * 
 * Esta anotação, indica que os métodos start() e end() serão do tipo @BeforeAll e @AfterAll,
 * diferente do teste da Model que era do tipo @BeforeEach e não possuia o método end()
 *  
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    
    @BeforeAll
    void start() throws ParseException {

    	LocalDate data = LocalDate.parse("2000-07-22", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	
        Usuario usuario = new Usuario(0L, "Giovanni Cerqueira", "giovanni@email.com.br", "giovanni123", data);
        
        /**
		 * A linha do if agora impede a duplicação dos dados. Caso ocorra a duplicação,
		 * o teste falhará.
		 * 
		 * O sinal de exclamação no inicio da linha do if significa Not (Não),
		 * logo este if verifica se o usuario não está presente no banco de dados,
		 * caso não esteja, o usuário será gravado
		 *  
		 */
        
        if(usuarioRepository.findByLogin(usuario.getLogin()) != null)
			usuarioRepository.save(usuario);
        
        usuario = new Usuario(0, "Marcelo Cerqueira", "marcelo@email.com.br", "marcelo123", data);

        if(usuarioRepository.findByLogin(usuario.getLogin()) != null)
            usuarioRepository.save(usuario);

        usuario = new Usuario(0, "Luzia Cerqueira", "luzia@email.com.br", "luzia123", data);

        if(usuarioRepository.findByLogin(usuario.getLogin()) != null)
            usuarioRepository.save(usuario);

       	usuario = new Usuario(0, "Harry Potter", "harry@email.com.br", "harry123", data);

        if(usuarioRepository.findByLogin(usuario.getLogin()) != null)
            usuarioRepository.save(usuario);
  }
    
    /**
	 * 
	 * Neste teste, vamos testar o funcionamento do método personalizado
	 * findByNome() que retorna um nome exatamente igual ao que foi digitado
	 * 
	 * Se o nome digitado for encontrado, o teste passou!
	 * 
	 */
    
    @Test
    @DisplayName("💾 Retorna o nome")
    public void findFirstByNomeRetornaNome() throws Exception {
        Usuario usuario = usuarioRepository.findByNome("Giovanni Cerqueira");
        assertTrue(usuario.getNome().equals("Giovanni Cerqueira"));
  }
    
    /**
   	 * 
   	 * Neste teste, vamos testar o funcionamento do método personalizado
   	 * findAllByNomeContainingIgnoreCase que retorna todos os usuários 
   	 * cujo nome contenha a String informada
   	 * 
   	 * Neste teste temos 3 usuários com o sobrenome Silva, logo ao executar
   	 * o método procurando pela String Silva, a lista terá 3 usuários
   	 * 
   	 * Se o nosso objeto listaDeUsuarios possuir 3 usuarios (size = 3), 
   	 * o teste passou!
   	 * 
   	 * Se você alterar o sobrenome de algum usuario com sobrenome Silva,
   	 * o teste irá falhar
   	 * 
   	 */
    
    @Test
    @DisplayName("💾 Retorna 3 usuarios")
    public void findAllByNomeContainingIgnoreCaseRetornaTresUsuarios() {
        List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Cerqueira");
        assertEquals(3, listaDeUsuarios.size());
  }
    
    /**
	 * 
	 * Após concluir todos os testes, apague os dados da tabela
	 * 
	 */
    
    @AfterAll
    public void end() {
        usuarioRepository.deleteAll();
    }
}