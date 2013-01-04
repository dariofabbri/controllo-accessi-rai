package it.dariofabbri.controlloaccessirai.service.utente;

import it.dariofabbri.controlloaccessirai.model.Utente;
import it.dariofabbri.controlloaccessirai.service.QueryResult;
import it.dariofabbri.controlloaccessirai.service.Service;

public interface UtenteService extends Service {

	QueryResult<Utente> list(
			Integer matricola,
			String username,
			String nome,
			String cognome,
			String tipoAccount,
			Integer offset,
			Integer limit);

	Utente retrieveByMatricola(Integer matricola);
	Utente retrieveByUsername(String username);

	void deleteByMatricola(Integer matricola);

	Utente create(Integer matricola, String username, String password, String nome, String cognome, String tipoAccount);

	Utente update(Integer id, String username, String password, String nome, String cognome, String tipoAccount);
}