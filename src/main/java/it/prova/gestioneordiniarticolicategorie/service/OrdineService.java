package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.ordine.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineService {

	
	
	public List<Ordine> list() throws Exception;
	
	public Ordine get(Long id) throws Exception;
	
	public void update(Ordine ordineInstance) throws Exception;
	
	public void insert(Ordine ordineInstance) throws Exception;
	
	public void delete(Long idOrdine) throws Exception;
	
	
	
	
	public void setOrdineDAO(OrdineDAO ordineDAO);	
}
