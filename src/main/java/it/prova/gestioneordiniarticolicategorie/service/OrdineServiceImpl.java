package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.dao.ordine.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.exception.OrdineConArticoloCollegatoException;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;


public class OrdineServiceImpl implements OrdineService {

	
	private OrdineDAO ordineDAO;
	
	
	@Override
	public void setOrdineDAO(OrdineDAO ordineDAO) {
		this.ordineDAO = ordineDAO;
	}


	@Override
	public List<Ordine> list() throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			ordineDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return ordineDAO.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}


	@Override
	public Ordine get(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			ordineDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return ordineDAO.get(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}


	@Override
	public void update(Ordine ordineInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			ordineDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			ordineDAO.update(ordineInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
		
	}


	@Override
	public void insert(Ordine ordineInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			ordineDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			ordineDAO.insert(ordineInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
		
	}


	@Override
	public void delete(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			ordineDAO.setEntityManager(entityManager);
			
			
			if (!(ordineDAO.findByIdFetchingArticoli(id).getArticoli().isEmpty())) {
				throw new OrdineConArticoloCollegatoException("Questo ordine ha degli articoli");
			}
		

			// eseguo quello che realmente devo fare
			ordineDAO.delete(ordineDAO.get(id));

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
		
	}


	@Override
	public Ordine findByIdFetchingArticoli(Long id) {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			ordineDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
		    return	ordineDAO.findByIdFetchingArticoli(id);

			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}


	@Override
	public List<Ordine> listaOrdiniDeterminataCategoria(String codiceCategoria) {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			ordineDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
		    return	ordineDAO.listaOrdiniDeterminataCategoria(codiceCategoria);

			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}


	@Override
	public Ordine ordineSpedizioneRecenteDataCategoria(Categoria categoriaInput) {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			ordineDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
		    return	ordineDAO.ordineSpedizioneRecenteDataCategoria(categoriaInput);

			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}


	@Override
	public List<String> listaIndirizziDatoCodiceSerialeInput(String codiceSerialeInput) {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			ordineDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
		    return	ordineDAO.listaIndirizziDatoCodiceSerialeInput(codiceSerialeInput);

			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}



	
}
