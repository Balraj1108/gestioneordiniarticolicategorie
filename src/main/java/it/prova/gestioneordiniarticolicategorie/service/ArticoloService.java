package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.articolo.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;

public interface ArticoloService {

	public List<Articolo> list() throws Exception;
	
	public Articolo get(Long id) throws Exception;
	
	public void update(Articolo articoloInstance) throws Exception;
	
	public void insert(Articolo articoloInstance) throws Exception;
	
	public void delete(Long idArticolo) throws Exception;
	
	
	
	
	public void setArticoloDAO(ArticoloDAO articoloDAO);
}
