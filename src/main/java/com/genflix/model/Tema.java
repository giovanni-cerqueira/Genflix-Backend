package com.genflix.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_temas")
public class Tema{
    
    @Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String categoria;
    
	private String descricao;
	
	private String categoriaAdm;
	
	private String descricaoAdm;
	
	

	@OneToMany(mappedBy = "tema", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("tema")
	private List<Postagem> postagem;
	
	@Transient
	private int qtdTema; // Atributo Trendtopics
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getCategoriaAdm() {
		return categoriaAdm;
	}

	public void setCategoriaAdm(String categoriaAdm) {
		this.categoriaAdm = categoriaAdm;
	}

	public String getDescricaoAdm() {
		return descricaoAdm;
	}

	public void setDescricaoAdm(String descricaoAdm) {
		this.descricaoAdm = descricaoAdm;
	}
	
	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}

	public int getQtdTema() {
		return qtdTema;
	}

	public void setQtdTema(int qtdTema) {
		this.qtdTema = qtdTema;
	}
	
	
}
