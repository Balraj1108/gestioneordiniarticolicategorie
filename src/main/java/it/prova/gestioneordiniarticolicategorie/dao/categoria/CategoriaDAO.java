package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface CategoriaDAO extends IBaseDAO<Categoria> {

	public List<Categoria> listaCategorieArticoliDiUnOrdine(Ordine ordineInput) throws Exception;
	
	public List<String> listaCodiciCategorieDatoMeseAnnoInput(int mese, int anno) throws Exception;
	
	public Categoria findCategoriaByIdFetchingArticoli(Long id);
	
	
}
