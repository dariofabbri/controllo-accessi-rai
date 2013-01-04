package it.dariofabbri.controlloaccessirai.service;

import it.dariofabbri.controlloaccessirai.service.utente.UtenteService;
import it.dariofabbri.controlloaccessirai.service.utente.UtenteServiceImpl;

public class ServiceFactory {
	
	public static UtenteService createUtenteService() {
		
		UtenteService service = SessionDecorator.<UtenteService>createProxy(new UtenteServiceImpl(), UtenteService.class);
		return service;
	}
}