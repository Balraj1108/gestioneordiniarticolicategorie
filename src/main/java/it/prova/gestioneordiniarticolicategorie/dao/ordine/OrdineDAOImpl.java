package it.prova.gestioneordiniarticolicategorie.dao.ordine;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class OrdineDAOImpl implements OrdineDAO {

	
	private EntityManager entityManager;
	
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Ordine> list() throws Exception {
		return entityManager.createQuery("from Ordine", Ordine.class).getResultList();
	}

	@Override
	public Ordine get(Long id) throws Exception {
		return entityManager.find(Ordine.class, id);
	}

	@Override
	public void update(Ordine input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
		
	}

	@Override
	public void insert(Ordine input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
		
	}

	@Override
	public void delete(Ordine input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(input));
		
	}

	@Override
	public Ordine findByIdFetchingArticoli(Long id) {
		TypedQuery<Ordine> query = entityManager.createQuery("select o FROM Ordine o left join fetch o.articoli a where o.id = :idOrdine",Ordine.class);
		query.setParameter("idOrdine", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Ordine> listaOrdiniDeterminataCategoria(String codiceCategoria) {
		TypedQuery<Ordine> query = entityManager.createQuery("select o FROM Ordine o join fetch o.articoli a join fetch a.categorie c where codice = ?1",Ordine.class);
		query.setParameter(1, codiceCategoria);
		return query.getResultList();
	}

	@Override
	public Ordine ordineSpedizioneRecenteDataCategoria(Categoria categoriaInput) {
																														
		TypedQuery<Ordine> query = entityManager.createQuery("select o FROM Ordine o join fetch o.articoli a join fetch a.categorie c where c = ?1 and dataspedizione <= ?2 order by dataspedizione desc",Ordine.class);
		query.setParameter(1, categoriaInput);
		query.setParameter(2, new Date());
		return query.getResultList().get(0);
	}

	@Override
	public List<String> listaIndirizziDatoCodiceSerialeInput(String codiceSerialeInput) {
		TypedQuery<String> query = entityManager.createQuery
				("select DISTINCT o.indirizzoSpedizione  from Ordine o join  o.articoli a join  a.categorie c where numeroseriale like ?1",String.class);
		query.setParameter(1, "%"  + codiceSerialeInput + "%");
		return query.getResultList();
	}


	
	
}
