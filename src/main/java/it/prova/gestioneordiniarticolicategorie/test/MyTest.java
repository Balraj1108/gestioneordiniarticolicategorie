package it.prova.gestioneordiniarticolicategorie.test;

import java.time.LocalDateTime;
import java.util.Date;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.exception.OrdineConArticoloCollegatoException;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
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
			
			testInserimentoNuovoArticolo(ordineServiceInstance,articoloServiceInstance );
			
			testAggiornamentoArticoloEsistente(ordineServiceInstance,articoloServiceInstance);
			
			testRimozioneArticoloLegatoAdOrdine(ordineServiceInstance,articoloServiceInstance);
			
			testInserimentoNuovaCategoria(categoriaServiceInstance);
			
			testAggiornamentoCategoria(categoriaServiceInstance);
			
			testAggiungiArticoloAdCategoria(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			
			testAggiungiCategoriaAdArticolo(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			
			testRimozioneArticoloCollegato(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			
			testRimozioneCategoriaCollegata(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			
			testRimozioneOrdineCollegato(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			
			
			
			
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
			throw new RuntimeException("Test Inserimento ordine fallito");
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
	
	private static void testInserimentoNuovoArticolo(OrdineService ordineServiceInstance, 
			ArticoloService articoloServiceInstance) throws Exception{
		System.out.println(".......testInserimentoNuovoArticolo inizio.............");
		
		Ordine ordineTest = new Ordine("balraj","via ciao", new Date(), new Date());
		
		ordineServiceInstance.insert(ordineTest);
		
		Articolo articoloTest = new Articolo("acqua","45das", 5, new Date());
		
		articoloTest.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest);
		
		if (articoloTest.getId() == null) {
			throw new RuntimeException("Test Inserimento articolo fallito");
		}
		
		
		
		System.out.println(".......testInserimentoNuovoArticolo fine: PASSED.............");	
		
	}
	
	private static void testAggiornamentoArticoloEsistente(OrdineService ordineServiceInstance, 
			ArticoloService articoloServiceInstance) throws Exception{
		System.out.println(".......testAggiornamentoArticoloEsistente inizio.............");
		
		Ordine ordineTest = new Ordine("balraj","via ciao", new Date(), new Date());
		
		ordineServiceInstance.insert(ordineTest);
		
		Articolo articoloTest = new Articolo("acqua","45das", 5, new Date());
		
		articoloTest.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest);
		
		
		LocalDateTime createDateTimeIniziale = articoloTest.getCreateDateTime();
		LocalDateTime updateDateTimeIniziale = articoloTest.getUpdateDateTime();

		// **************************************************************************************************
		// **************************************************************************************************
		// all'inizio DOVREBBERO essere uguali, infatti a volte non lo sono per
		// questione di 10^-4 secondi
		// soluzione: riprovare!!! Se diventa sistematico commentare le due righe
		// successive
		if (!createDateTimeIniziale.equals(updateDateTimeIniziale))
			throw new RuntimeException("testAggiornamentoArticoloEsistente fallito: le date non coincidono ");
		// **************************************************************************************************
		// **************************************************************************************************

		// ora modifico il record
		articoloTest.setPrezzoSingolo(10);
		articoloServiceInstance.update(articoloTest);

		// se la nuova data aggiornamento risulta precedente a quella iniziale: errore
		if (articoloTest.getUpdateDateTime().isAfter(updateDateTimeIniziale))
			throw new RuntimeException("testAggiornamentoArticoloEsistente fallito: le date di modifica sono disallineate ");

		// la data creazione deve essere uguale a quella di prima
		if (!articoloTest.getCreateDateTime().equals(createDateTimeIniziale))
			throw new RuntimeException("testAggiornamentoArticoloEsistente fallito: la data di creazione è cambiata ");
		
		
		System.out.println(".......testAggiornamentoArticoloEsistente fine: PASSED.............");
	}
	
	private static void testRimozioneArticoloLegatoAdOrdine(OrdineService ordineServiceInstance, 
			ArticoloService articoloServiceInstance) throws Exception{
		System.out.println(".......testRimozioneArticoloLegatoAdOrdine inizio.............");
		
		Ordine ordineTest = new Ordine("luca","via test rimozione articolo", new Date(), new Date());
		
		ordineServiceInstance.insert(ordineTest);
		
		Articolo articoloTest = new Articolo("coca-cola","45das", 5, new Date());
		
		articoloTest.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest);
		
		//test
		articoloServiceInstance.delete(articoloTest.getId());
		
		//System.out.println(articoloTest.getId());
		
		
		if (articoloServiceInstance.get(articoloTest.getId()) != null) {
			throw new RuntimeException("Test Fallito: Articolo non rimosso");
		}
		
		
		
		System.out.println(".......testRimozioneArticoloLegatoAdOrdine fine: PASSED.............");
	}
	
	private static void testInserimentoNuovaCategoria(CategoriaService categoriaServiceInstance) throws Exception{
		System.out.println(".......testInserimentoNuovaCategoria inizio.............");
		
		Categoria categoriaTest = new Categoria("cose di casa" ,"hdka4");
		
		categoriaServiceInstance.insert(categoriaTest);
		
		if (categoriaTest.getId() == null) {
			throw new RuntimeException("Test Inserimento categoria fallito");
		}
		
		
		
		
		System.out.println(".......testInserimentoNuovaCategoria fine: PASSED.............");
		
	}
	
	private static void testAggiornamentoCategoria(CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println(".......testAggiornamentoCategoria inizio.............");
		
		Categoria categoriaTest = new Categoria("cose di casa" ,"hdka4");
		
		categoriaServiceInstance.insert(categoriaTest);
		
		LocalDateTime createDateTimeIniziale = categoriaTest.getCreateDateTime();
		LocalDateTime updateDateTimeIniziale = categoriaTest.getUpdateDateTime();

		// **************************************************************************************************
		// **************************************************************************************************
		// all'inizio DOVREBBERO essere uguali, infatti a volte non lo sono per
		// questione di 10^-4 secondi
		// soluzione: riprovare!!! Se diventa sistematico commentare le due righe
		// successive
		if (!createDateTimeIniziale.equals(updateDateTimeIniziale))
			throw new RuntimeException("testAggiornamentoCategoria fallito: le date non coincidono ");
		// **************************************************************************************************
		// **************************************************************************************************

		// ora modifico il record
		categoriaTest.setDescrizione("cose di paese");
		categoriaServiceInstance.update(categoriaTest);

		// se la nuova data aggiornamento risulta precedente a quella iniziale: errore
		if (categoriaTest.getUpdateDateTime().isAfter(updateDateTimeIniziale))
			throw new RuntimeException("testAggiornamentoCategoria fallito: le date di modifica sono disallineate ");

		// la data creazione deve essere uguale a quella di prima
		if (!categoriaTest.getCreateDateTime().equals(createDateTimeIniziale))
			throw new RuntimeException("testAggiornamentoCategoria fallito: la data di creazione è cambiata ");		
		
		
		System.out.println(".......testAggiornamentoCategoria fine: PASSED.............");
	}
	
	
	private static void testAggiungiArticoloAdCategoria(OrdineService ordineServiceInstance, 
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance ) throws Exception{
		System.out.println(".......testAggiungiArticoloAdCategoria inizio.............");
		
		Ordine ordineTest = new Ordine("mario","via napoli", new Date(), new Date());
		
		ordineServiceInstance.insert(ordineTest);
		
		Articolo articoloTest = new Articolo("armadio","fd545", 5, new Date());
		
		articoloTest.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest);
		
		Categoria categoriaTest = new Categoria("cose di casa" ,"dsad8");
		
		categoriaServiceInstance.insert(categoriaTest);
		
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest, categoriaTest);
		
		
		System.out.println(".......testAggiungiArticoloAdCategoria fine: PASSED.............");
	}
	
	private static void testAggiungiCategoriaAdArticolo(OrdineService ordineServiceInstance, 
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance ) throws Exception{
		System.out.println(".......testAggiungiCategoriaAdArticolo inizio.............");
		
		Ordine ordineTest = new Ordine("mari","via napoli", new Date(), new Date());
		
		ordineServiceInstance.insert(ordineTest);
		
		Articolo articoloTest = new Articolo("armadi","fd54", 54, new Date());
		
		articoloTest.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest);
		
		Categoria categoriaTest = new Categoria("cose di cas" ,"dsad");
		
		categoriaServiceInstance.insert(categoriaTest);
		
		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloTest, categoriaTest);
		
		
		System.out.println(".......testAggiungiCategoriaAdArticolo fine: PASSED.............");
	}
	
	private static void testRimozioneArticoloCollegato(OrdineService ordineServiceInstance, 
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance ) throws Exception{
		System.out.println(".......testRimozioneArticoloCollegato inizio.............");
		
		Ordine ordineTest = new Ordine("mari","via napoli", new Date(), new Date());
		
		ordineServiceInstance.insert(ordineTest);
		
		Articolo articoloTest = new Articolo("armadi","fd54", 54, new Date());
		
		articoloTest.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest);
		
		Categoria categoriaTest = new Categoria("cose di cas" ,"dsad");
		
		categoriaServiceInstance.insert(categoriaTest);
		
		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloTest, categoriaTest);
		
		articoloServiceInstance.delete(articoloTest.getId());
		
		
		System.out.println(".......testRimozioneArticoloCollegato fine: PASSED.............");
	}
	
	private static void testRimozioneCategoriaCollegata(OrdineService ordineServiceInstance, 
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance ) throws Exception{
		System.out.println(".......testRimozioneCategoriaCollegata inizio.............");
		
		Ordine ordineTest = new Ordine("mari","via napoli", new Date(), new Date());
		
		ordineServiceInstance.insert(ordineTest);
		
		Articolo articoloTest = new Articolo("armadi","fd54", 54, new Date());
		
		articoloTest.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest);
		
		Categoria categoriaTest = new Categoria("cose di cas" ,"dsad");
		
		categoriaServiceInstance.insert(categoriaTest);
		
		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloTest, categoriaTest);
		
		articoloServiceInstance.scollegaArticoliDaCategoria(articoloTest.getId());
		
		categoriaServiceInstance.delete(categoriaTest.getId());
		
		
		System.out.println(".......testRimozioneCategoriaCollegata fine: PASSED.............");
	}
	
	
	private static void testRimozioneOrdineCollegato(OrdineService ordineServiceInstance, 
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance ) throws Exception{
		System.out.println(".......testRimozioneOrdineCollegato inizio.............");
		
		Ordine ordineTest = new Ordine("mari","via napoli", new Date(), new Date());
		
		ordineServiceInstance.insert(ordineTest);
		
		Articolo articoloTest = new Articolo("armadi","fd54", 54, new Date());
		
		articoloTest.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest);
		
		Categoria categoriaTest = new Categoria("cose di cas" ,"dsad");
		
		categoriaServiceInstance.insert(categoriaTest);
		
		
		try {
			
			ordineServiceInstance.delete(ordineTest.getId());
			
		} catch (OrdineConArticoloCollegatoException e) {
			// TODO: handle exception
		}
		
		articoloServiceInstance.delete(articoloTest.getId());
		
		ordineServiceInstance.delete(ordineTest.getId());
		
		
		
		System.out.println(".......testRimozioneOrdineCollegato fine: PASSED.............");
	}
	
	
	

}
