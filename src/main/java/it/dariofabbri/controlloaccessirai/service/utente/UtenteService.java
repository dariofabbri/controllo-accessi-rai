package it.dariofabbri.controlloaccessirai.service.utente;

import java.util.Map;

import it.dariofabbri.controlloaccessirai.model.Utente;
import it.dariofabbri.controlloaccessirai.service.QueryResult;
import it.dariofabbri.controlloaccessirai.service.Service;
import it.dariofabbri.controlloaccessirai.service.SortDirection;

public interface UtenteService extends Service {

	QueryResult<Utente> list(
			Integer matricola,
			String username,
			String nome,
			String cognome,
			String tipoAccount,
			Integer offset,
			Integer limit);

	QueryResult<Utente> list(
			int first,
			int pageSize,
			String sortCriteria,
			SortDirection sortDirection,
			Map<String, String> filters);

	Utente retrieveByMatricola(Integer matricola);
	Utente retrieveByUsername(String username);

	void deleteByMatricola(Integer matricola);

	Utente create(
			Integer matricola, 
			String username, 
			String password, 
			String nome, 
			String cognome, 
			String tipoAccount);

	Utente update(
			Integer matricola, 
			String username, 
			String nome, 
			String cognome, 
			String tipoAccount);

	Utente changePassword(
			Integer matricola, 
			String password); 
}