package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface CategoriaService {

	
	
	public List<Categoria> list() throws Exception;
	
	public Categoria get(Long id) throws Exception;
	
	public void update(Categoria categoriaInstance) throws Exception;
	
	public void insert(Categoria categoriaInstance) throws Exception;
	
	public void delete(Long idCategoria) throws Exception;
	
	
	
	
	public void setCategoriaDAO(CategoriaDAO categoriaDAO);	
}
