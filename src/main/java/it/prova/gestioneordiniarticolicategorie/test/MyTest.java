package it.prova.gestioneordiniarticolicategorie.test;

import java.time.LocalDateTime;
import java.util.Date;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;
import it.prova.gestioneordiniarticolicategorie.service.ArticoloService;
import it.prova.gestioneordiniarticolicategorie.service.CategoriaService;
import it.prova.gestioneordiniarticolicategorie.service.MyServiceFactory;
import it.prova.gestioneordiniarticolicategorie.service.OrdineService;


public class MyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		OrdineService ordineServiceInstance = MyServiceFactory.getOrdineServiceInstance();
		ArticoloService articoloServiceInstance = MyServiceFactory.getArticoloServiceInstance();
		CategoriaService categoriaServiceInstance = MyServiceFactory.getCategoriaServiceInstance();
		
		try {
			
			
			testInserimentoNuovoOrdine(ordineServiceInstance);
			
			testAggiornamentoOrdineEsistente(ordineServiceInstance);
			
			
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
			EntityManagerUtil.shutdown();
		}
	}
	
	private static void testInserimentoNuovoOrdine(OrdineService ordineServiceInstance) throws Exception{
		System.out.println(".......testInserimentoNuovoOrdine inizio.............");
		
		Ordine ordineTest = new Ordine("balraj","via ciao", new Date(), new Date());
		
		ordineServiceInstance.insert(ordineTest);
		
		if (ordineTest.getId() == null) {
			throw new RuntimeException("Test Inserimento fallito");
		}
		
		
		System.out.println(".......testInserimentoNuovoOrdine fine: PASSED.............");
	}
	
	private static void testAggiornamentoOrdineEsistente(OrdineService ordineServiceInstance) throws Exception{
		
		System.out.println(".......testAggiornamentoOrdineEsistente inizio.............");
		
		Ordine ordineTest = new Ordine("balraj","via ciao", new Date(), new Date());
		
		ordineServiceInstance.insert(ordineTest);
		
		
		// ora mi salvo da parte le date di creazione ed update
		LocalDateTime createDateTimeIniziale = ordineTest.getCreateDateTime();
		LocalDateTime updateDateTimeIniziale = ordineTest.getUpdateDateTime();

		// **************************************************************************************************
		// **************************************************************************************************
		// all'inizio DOVREBBERO essere uguali, infatti a volte non lo sono per
		// questione di 10^-4 secondi
		// soluzione: riprovare!!! Se diventa sistematico commentare le due righe
		// successive
		if (!createDateTimeIniziale.equals(updateDateTimeIniziale))
			throw new RuntimeException("testAggiornamentoOrdineEsistente fallito: le date non coincidono ");
		// **************************************************************************************************
		// **************************************************************************************************

		// ora modifico il record
		ordineTest.setIndirizzoSpedizione("Via Roma 2");
		ordineServiceInstance.update(ordineTest);

		// se la nuova data aggiornamento risulta precedente a quella iniziale: errore
		if (ordineTest.getUpdateDateTime().isAfter(updateDateTimeIniziale))
			throw new RuntimeException("testAggiornamentoOrdineEsistente fallito: le date di modifica sono disallineate ");

		// la data creazione deve essere uguale a quella di prima
		if (!ordineTest.getCreateDateTime().equals(createDateTimeIniziale))
			throw new RuntimeException("testAggiornamentoOrdineEsistente fallito: la data di creazione è cambiata ");
		
		
		System.out.println(".......testAggiornamentoOrdineEsistente fine: PASSED.............");	
		
		
	}

}
