package com.genflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genflix.model.Postagem;
import com.genflix.repository.PostagemRepository;
import com.genflix.service.PostagemService;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private PostagemService postagemService;

	@GetMapping
	public ResponseEntity<List<Postagem>> GetAll() {
		return ResponseEntity.ok(postagemRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> GetById(@PathVariable long id) {
		return postagemRepository.findById(id)
			.map(resp -> ResponseEntity.ok(resp))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@GetMapping("/criticas/{criticas}")
	public ResponseEntity<List<Postagem>> getAllCriticas(@PathVariable String criticas){
		return ResponseEntity.ok(postagemRepository.findAllByCriticasContainingIgnoreCase(criticas));
	}
	
	@GetMapping("/filmes")
	public ResponseEntity<List<Postagem>> getAllFilmes(){
		return ResponseEntity.ok(postagemRepository.filmes());
	}
	
	@GetMapping("/series")
	public ResponseEntity<List<Postagem>> getAllSeries(){
		return ResponseEntity.ok(postagemRepository.series());
	}

	@PostMapping
	public ResponseEntity<Postagem> postPostagem (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	}
	
	@PutMapping
	public ResponseEntity<Postagem> putPostagem (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
	}
			
	@DeleteMapping("/{id}")
	public void deletePostagem(@PathVariable long id) {
		postagemRepository.deleteById(id);

	}	
	
	//Curtir postagem
	 
	@PutMapping("/curtir/{id}")
	public ResponseEntity<Postagem> putCurtirPostagemId (@PathVariable Long id){
		
		return ResponseEntity.status(HttpStatus.OK).body(postagemService.curtir(id));
	
	}
	
	//Descurtir postagem

	@PutMapping("/descurtir/{id}")
	public ResponseEntity<Postagem> putDescurtirPostagemId (@PathVariable Long id){
		
		return ResponseEntity.status(HttpStatus.OK).body(postagemService.descurtir(id));
	
	}
	
}
