package it.prova.gestioneordiniarticolicategorie.dao.ordine;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineDAO extends IBaseDAO<Ordine>  {

	
	public Ordine findByIdFetchingArticoli(Long id);
	
	public List<Ordine> listaOrdiniDeterminataCategoria(String codiceCategoria);
	
	public Ordine ordineSpedizioneRecenteDataCategoria(Categoria categoriaInput);
	
	public List<String> listaIndirizziDatoCodiceSerialeInput(String codiceSerialeInput);
	
	
}
