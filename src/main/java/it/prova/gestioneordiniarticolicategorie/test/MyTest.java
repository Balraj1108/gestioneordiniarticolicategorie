package it.prova.gestioneordiniarticolicategorie.test;

import java.text.SimpleDateFormat;
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
			
			testListaOrdiniDeterminataCategoria(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			
			testListaCategorieArticoliDiUnOrdine(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			
			testSommaPrezziArticoliLegatiCategoria(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			
			testOrdineSpedizioneRecenteDataCategoria(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			
			testListaCodiciCategorieDatoMeseAnnoInput(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			
			testSommaPrezziArticoliStessoDestinatario(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			
			testListaIndirizziDatoCodiceSerialeInput(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			
			testListaArticoliSituazioneStrana(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			
			
			
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
		
		Categoria categoriaRicaricata = categoriaServiceInstance.findCategoriaByIdFetchingArticoli(categoriaTest.getId());
		
		if (categoriaRicaricata.getArticoli().isEmpty()) {
			throw new RuntimeException("testAggiungiArticoloAdCategoria FALLITO: collegamento non riuscito");
		}
		
		
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
		
		Articolo articoloRicaricato = articoloServiceInstance.findByIdFetchingCategorie(articoloTest.getId());
		
		if (articoloRicaricato.getCategorie().isEmpty()) {
			throw new RuntimeException("testAggiungiCategoriaAdArticolo FALLITO: collegamento non riuscito");
		}
		
		
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
		
		if (articoloServiceInstance.get(articoloTest.getId()) != null) {
			throw new RuntimeException("testRimozioneArticoloCollegato FALLITO: articolo non rimosso");
		}
		
		
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
		
		if (categoriaServiceInstance.get(categoriaTest.getId()) != null) {
			throw new RuntimeException("testRimozioneCategoriaCollegata FALLITO: categoria non rimossa");
		}
		
		
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
		
		if (ordineServiceInstance.get(ordineTest.getId()) != null) {
			throw new RuntimeException("testRimozioneOrdineCollegato FALLITO: ordine non rimosso");
		}
		
		
		
		System.out.println(".......testRimozioneOrdineCollegato fine: PASSED.............");
	}
	
	
	private static void testListaOrdiniDeterminataCategoria(OrdineService ordineServiceInstance, 
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance ) throws Exception{
		System.out.println(".......testListaOrdiniDeterminataCategoria inizio.............");
		
		Ordine ordineTest = new Ordine("mari","via napoli", new Date(), new Date());
		
		ordineServiceInstance.insert(ordineTest);
		
		Articolo articoloTest = new Articolo("armadi","fd54", 54, new Date());
		
		articoloTest.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest);
		
		Categoria categoriaTest = new Categoria("cose di cas" ,"dsa5d");
		
		categoriaServiceInstance.insert(categoriaTest);
		
		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloTest, categoriaTest);
		
		String codiceCategoriaTest = categoriaTest.getCodice();
		
		//System.out.println(ordineServiceInstance.listaOrdiniDeterminataCategoria(codiceCategoriaTest).size());
		
		if (ordineServiceInstance.listaOrdiniDeterminataCategoria(codiceCategoriaTest).size() != 1) {
			throw new RuntimeException("testListaOrdiniDeterminataCategoria FALLITO: dati non coincidono");
		}

		
		
		System.out.println(".......testListaOrdiniDeterminataCategoria fine: PASSED.............");
	}
	
	
	private static void testListaCategorieArticoliDiUnOrdine(OrdineService ordineServiceInstance, 
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance ) throws Exception{
		System.out.println(".......testListaCategorieArticoliDiUnOrdine inizio.............");
		
		Ordine ordineTest = new Ordine("mari","via napoli", new Date(), new Date());
		
		ordineServiceInstance.insert(ordineTest);
		
		Articolo articoloTest = new Articolo("armadi","fd54", 54, new Date());
		
		articoloTest.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest);
		
		Articolo articoloTest1 = new Articolo("scrivania","f465d54", 4, new Date());
		
		articoloTest1.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest1);
		
		Categoria categoriaTest = new Categoria("cose di casa" ,"dsa5d");
		
		categoriaServiceInstance.insert(categoriaTest);
		
		Categoria categoriaTest1 = new Categoria("cose di ufficio" ,"d564565d");
		
		categoriaServiceInstance.insert(categoriaTest1);
		
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest1, categoriaTest1);
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest, categoriaTest);
		
		
		//System.out.println(categoriaServiceInstance.listaCategorieArticoliDiUnOrdine(ordineTest).size());
		
		if (categoriaServiceInstance.listaCategorieArticoliDiUnOrdine(ordineTest).size() != 2) {
			throw new RuntimeException("testListaCategorieArticoliDiUnOrdine FALLITO: dati non coincidono");
		}

		
		
		System.out.println(".......testListaCategorieArticoliDiUnOrdine fine: PASSED.............");
	}
	
	private static void testSommaPrezziArticoliLegatiCategoria(OrdineService ordineServiceInstance, 
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance ) throws Exception{
		System.out.println(".......testSommaPrezziArticoliLegatiCategoria inizio.............");
		
		Ordine ordineTest = new Ordine("mari","via napoli", new Date(), new Date());
		
		ordineServiceInstance.insert(ordineTest);
		
		Articolo articoloTest = new Articolo("armadi","fd54", 54, new Date());
		
		articoloTest.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest);
		
		Articolo articoloTest1 = new Articolo("scrivania","f465d54", 4, new Date());
		
		articoloTest1.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest1);
		
		Categoria categoriaTest = new Categoria("cose di casa" ,"dsa5d");
		
		categoriaServiceInstance.insert(categoriaTest);
		
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest1, categoriaTest);
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest, categoriaTest);
		
		
		
		System.out.println(articoloServiceInstance.sommaPrezziArticoliLegatiCategoria(categoriaTest));
		

		if (articoloServiceInstance.sommaPrezziArticoliLegatiCategoria(categoriaTest) != 58) {
			throw new RuntimeException("testSommaPrezziArticoliLegatiCategoria FALLITO: dati non coincidono");
		}
		
		
		System.out.println(".......testSommaPrezziArticoliLegatiCategoria fine: PASSED.............");
	}
	
	private static void testOrdineSpedizioneRecenteDataCategoria(OrdineService ordineServiceInstance, 
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance ) throws Exception{
		System.out.println(".......testOrdineSpedizioneRecenteDataCategoria inizio.............");
		
		Date dataTest1 = new SimpleDateFormat("dd-MM-yyyy").parse("10-10-2020");
		Ordine ordineTest = new Ordine("mari","via napoli", dataTest1, dataTest1);
		ordineServiceInstance.insert(ordineTest);
		Articolo articoloTest = new Articolo("armadi","fd54", 54, new Date());
		articoloTest.setOrdine(ordineTest);
		articoloServiceInstance.insert(articoloTest);
		Articolo articoloTest1 = new Articolo("scrivania","f465d54", 4, new Date());
		articoloTest1.setOrdine(ordineTest);
		articoloServiceInstance.insert(articoloTest1);
		Categoria categoriaTest = new Categoria("cose di casa" ,"dsa5d");
		categoriaServiceInstance.insert(categoriaTest);
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest1, categoriaTest);
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest, categoriaTest);
		
		Date dataTest = new SimpleDateFormat("dd-MM-yyyy").parse("10-10-2021");
		
		Ordine ordineTest1 = new Ordine("luigi","via bologna", dataTest, dataTest);
		ordineServiceInstance.insert(ordineTest1);
		Articolo articoloTest3 = new Articolo("armdsadi","fddsd54", 44, new Date());
		articoloTest3.setOrdine(ordineTest1);
		articoloServiceInstance.insert(articoloTest3);
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest3, categoriaTest);
		
		
		//System.out.println(ordineServiceInstance.ordineSpedizioneRecenteDataCategoria(categoriaTest).getDataSpedizione());
		
		if (!(ordineServiceInstance.ordineSpedizioneRecenteDataCategoria(categoriaTest).getNomeDestinatario().equals("luigi"))) {
			throw new RuntimeException("testOrdineSpedizioneRecenteDataCategoria FALLITO: dati non coincidono");
		}
		
		
		System.out.println(".......testOrdineSpedizioneRecenteDataCategoria fine: PASSED.............");
	}
	
	
	private static void testListaCodiciCategorieDatoMeseAnnoInput(OrdineService ordineServiceInstance, 
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance ) throws Exception{
		System.out.println(".......testListaCodiciCategorieDatoMeseAnnoInput inizio.............");
		
		Date dataTest = new SimpleDateFormat("dd-MM-yyyy").parse("11-11-2020");
		Ordine ordineTest = new Ordine("mari","via napoli", dataTest, dataTest);
		
		ordineServiceInstance.insert(ordineTest);
		
		Articolo articoloTest = new Articolo("armadi","fd54", 54, dataTest);
		
		articoloTest.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest);
		
		Articolo articoloTest1 = new Articolo("scrivania","f465d54", 4, dataTest);
		
		articoloTest1.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest1);
		
		Categoria categoriaTest = new Categoria("cose di casa" ,"dsa5d");
		
		categoriaServiceInstance.insert(categoriaTest);
		
		Categoria categoriaTest1 = new Categoria("cose di ufficio" ,"d564565d");
		
		categoriaServiceInstance.insert(categoriaTest1);
		
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest1, categoriaTest1);
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest, categoriaTest);
		
		
		//System.out.println(categoriaServiceInstance.listaCodiciCategorieDatoMeseAnnoInput(11, 2020));
		
		if (categoriaServiceInstance.listaCodiciCategorieDatoMeseAnnoInput(11, 2020).size() != 2) {
			throw new RuntimeException("testListaCodiciCategorieDatoMeseAnnoInput FALLITO: dati non coincidono");
		}
		

		
		
		System.out.println(".......testListaCodiciCategorieDatoMeseAnnoInput fine: PASSED.............");
	}
	
	
	private static void testSommaPrezziArticoliStessoDestinatario(OrdineService ordineServiceInstance, 
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance ) throws Exception{
		System.out.println(".......testSommaPrezziArticoliStessoDestinatario inizio.............");
		
		Ordine ordineTest = new Ordine("mariop","via napoli", new Date(), new Date());
		
		ordineServiceInstance.insert(ordineTest);
		
		Articolo articoloTest = new Articolo("armadi","fd54", 54, new Date());
		
		articoloTest.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest);
		
		Articolo articoloTest1 = new Articolo("scrivania","f465d54", 40, new Date());
		
		articoloTest1.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest1);
		
		Categoria categoriaTest = new Categoria("cose di casa" ,"dsa5d");
		
		categoriaServiceInstance.insert(categoriaTest);
		
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest1, categoriaTest);
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest, categoriaTest);
		
		
		String nome = "mariop";
		String indirizzo = "via napoli";
		
		//System.out.println(articoloServiceInstance.sommaPrezziArticoliStessoDestinatario(indirizzo, nome));
		
		if (articoloServiceInstance.sommaPrezziArticoliStessoDestinatario(indirizzo, nome) != 94) {
			throw new RuntimeException("testSommaPrezziArticoliStessoDestinatario FALLITO: dati non coincidono");
		}
		
		System.out.println(".......testSommaPrezziArticoliStessoDestinatario fine: PASSED.............");
	}
	
	
	private static void testListaIndirizziDatoCodiceSerialeInput(OrdineService ordineServiceInstance, 
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance ) throws Exception{
		System.out.println(".......testListaIndirizziDatoCodiceSerialeInput inizio.............");
		
		Date dataTest1 = new SimpleDateFormat("dd-MM-yyyy").parse("10-10-2020");
		Ordine ordineTest = new Ordine("mari","via napoli 40", dataTest1, dataTest1);
		ordineServiceInstance.insert(ordineTest);
		Articolo articoloTest = new Articolo("armadi","c-lky5-a", 54, new Date());
		articoloTest.setOrdine(ordineTest);
		articoloServiceInstance.insert(articoloTest);
		Articolo articoloTest1 = new Articolo("scrivania","n-lky5-o", 4, new Date());
		articoloTest1.setOrdine(ordineTest);
		articoloServiceInstance.insert(articoloTest1);
		Categoria categoriaTest = new Categoria("cose di casa" ,"dsa5d");
		categoriaServiceInstance.insert(categoriaTest);
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest1, categoriaTest);
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest, categoriaTest);
		
		Date dataTest = new SimpleDateFormat("dd-MM-yyyy").parse("10-10-2021");
		
		Ordine ordineTest1 = new Ordine("luigi","via bologna 55", dataTest, dataTest);
		ordineServiceInstance.insert(ordineTest1);
		Articolo articoloTest3 = new Articolo("armdsadi","p-lky5-l", 44, new Date());
		articoloTest3.setOrdine(ordineTest1);
		articoloServiceInstance.insert(articoloTest3);
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest3, categoriaTest);
		
		
		String codiceSeriale = "lky5";
		
		//System.out.println(ordineServiceInstance.listaIndirizziDatoCodiceSerialeInput(codiceSeriale));
		
		if (ordineServiceInstance.listaIndirizziDatoCodiceSerialeInput(codiceSeriale).size() != 2) {
			throw new RuntimeException("testListaIndirizziDatoCodiceSerialeInput FALLITO: dati non coincidono");
		}
		
		System.out.println(".......testListaIndirizziDatoCodiceSerialeInput fine: PASSED.............");
	}
	
	
	private static void testListaArticoliSituazioneStrana(OrdineService ordineServiceInstance, 
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance ) throws Exception{
		System.out.println(".......testListaArticoliSituazioneStrana inizio.............");
		
		Date dataTest = new SimpleDateFormat("dd-MM-yyyy").parse("10-10-2020");
		Ordine ordineTest = new Ordine("mariop","via napoli", new Date(), dataTest);
		
		ordineServiceInstance.insert(ordineTest);
		
		Articolo articoloTest = new Articolo("armadi","fd54", 54, new Date());
		
		articoloTest.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest);
		
		Articolo articoloTest1 = new Articolo("scrivania","f465d54", 40, new Date());
		
		articoloTest1.setOrdine(ordineTest);
		
		articoloServiceInstance.insert(articoloTest1);
		
		Categoria categoriaTest = new Categoria("cose di casa" ,"dsa5d");
		
		categoriaServiceInstance.insert(categoriaTest);
		
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest1, categoriaTest);
		categoriaServiceInstance.aggiungiArticoloACategoria(articoloTest, categoriaTest);
		
		
		//System.out.println(articoloServiceInstance.listaArticoliSituazioneStrana().size());
		
		if (articoloServiceInstance.listaArticoliSituazioneStrana().size() != 2) {
			throw new RuntimeException("testListaArticoliSituazioneStrana FALLITO: dati non coicidono");
		}
		
		
		System.out.println(".......testListaArticoliSituazioneStrana fine: PASSED.............");
	}
	

}
