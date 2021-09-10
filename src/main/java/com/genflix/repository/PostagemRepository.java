package com.genflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.genflix.model.Postagem;

@Repository
@Transactional(readOnly = true)
public interface PostagemRepository extends JpaRepository<Postagem, Long> {

	public List <Postagem> findAllByTituloContainingIgnoreCase(String titulo);
	public List <Postagem> findAllByCriticasContainingIgnoreCase(String criticas);
	

	/**
	 * 
	 * Consulta Nativa - Conta o numero de postagens por tema
	 * 
	 */
	
	@Query(value = "select count(tema_id) from tb_postagens where tema_id = :id", nativeQuery = true)
	public int countPosts2(@Param("id") long id);
	
	@Query(value = "select * from tb_postagens where criticas is not null", nativeQuery = true)
	public List<Postagem> criticas();
	
	@Query(value = "select * from tb_postagens where criticas is null", nativeQuery = true)
	public List<Postagem> filmes();
	
	@Query(value = "select * from tb_postagens where titulo is null", nativeQuery = true)
	public List<Postagem> series();

}
