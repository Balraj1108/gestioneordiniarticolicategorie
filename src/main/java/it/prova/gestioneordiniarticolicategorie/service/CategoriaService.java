package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface CategoriaService {

	
	
	public List<Categoria> list() throws Exception;
	
	public Categoria get(Long id) throws Exception;
	
	public void update(Categoria categoriaInstance) throws Exception;
	
	public void insert(Categoria categoriaInstance) throws Exception;
	
	public void delete(Long idCategoria) throws Exception;
	
	public void aggiungiArticoloACategoria(Articolo articoloInstance, Categoria categoriaInstance ) throws Exception;
	
	public List<Categoria> listaCategorieArticoliDiUnOrdine(Ordine ordineInput) throws Exception;
	
	public List<String> listaCodiciCategorieDatoMeseAnnoInput(int mese, int anno) throws Exception;
	
	public Categoria findCategoriaByIdFetchingArticoli(Long id);
	
	
	
	
	
	
	public void setCategoriaDAO(CategoriaDAO categoriaDAO);	
}
