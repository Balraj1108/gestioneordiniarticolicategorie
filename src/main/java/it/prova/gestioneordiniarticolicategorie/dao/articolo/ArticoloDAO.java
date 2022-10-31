package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface ArticoloDAO extends IBaseDAO<Articolo> {

	public void scollegaArticoliDaCategoria(Long idArticolo) throws Exception;
	
	public Long sommaPrezziArticoliLegatiCategoria(Categoria categoriaInput) throws Exception;
	
	public Long sommaPrezziArticoliStessoDestinatario(String indirizzoInput, String nomeInput) throws Exception;
	
	public List<Articolo> listaArticoliSituazioneStrana() throws Exception;
	
	public Articolo findByIdFetchingCategorie(Long id);
	
}
