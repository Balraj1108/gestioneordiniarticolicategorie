package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class CategoriaDAOImpl implements CategoriaDAO {

	
	private EntityManager entityManager;
	
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Categoria> list() throws Exception {
		return entityManager.createQuery("from Categoria", Categoria.class).getResultList();
	}

	@Override
	public Categoria get(Long id) throws Exception {
		return entityManager.find(Categoria.class, id);
	}

	@Override
	public void update(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
		
	}

	@Override
	public void insert(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
		
	}

	@Override
	public void delete(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(input));
		
	}

	@Override
	public List<Categoria> listaCategorieArticoliDiUnOrdine(Ordine ordineInput) throws Exception {
		TypedQuery<Categoria> query = entityManager.createQuery("select distinct c FROM Categoria c join fetch c.articoli a where a.ordine = ?1",Categoria.class);
		query.setParameter(1, ordineInput);
		return query.getResultList();
	}

	@Override
	public List<String> listaCodiciCategorieDatoMeseAnnoInput(int mese, int anno) throws Exception {
		TypedQuery<String> query = entityManager.createQuery
				//select o FROM Ordine o join fetch o.articoli a join fetch a.categorie c where c = ?1 and dataspedizione <= ?2 order by dataspedizione desc
				("select DISTINCT c.codice  from Ordine o join  o.articoli a join  a.categorie c where year(dataspedizione) = ?1 and month(dataspedizione) = ?2", String.class);
		query.setParameter(1, anno);
		query.setParameter(2, mese);
		return query.getResultList();
	}

	@Override
	public Categoria findCategoriaByIdFetchingArticoli(Long id) {
		TypedQuery<Categoria> query = entityManager.createQuery("select c FROM Categoria c join fetch c.articoli a where c.id = :idCategoria",Categoria.class);
		query.setParameter("idCategoria", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}
	
	
}
