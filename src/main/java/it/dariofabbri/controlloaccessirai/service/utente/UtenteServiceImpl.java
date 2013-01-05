package it.dariofabbri.controlloaccessirai.service.utente;

import java.util.Map;

import it.dariofabbri.controlloaccessirai.model.Utente;
import it.dariofabbri.controlloaccessirai.service.AbstractService;
import it.dariofabbri.controlloaccessirai.service.NotFoundException;
import it.dariofabbri.controlloaccessirai.service.QueryResult;
import it.dariofabbri.controlloaccessirai.service.SortDirection;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.Query;

public class UtenteServiceImpl extends AbstractService implements UtenteService {

	private static final int HASH_ITERATIONS = 10000;
	
	@Override
	public QueryResult<Utente> list(
			Integer matricola,
			String username,
			String nome,
			String cognome,
			String tipoAccount,
			Integer offset,
			Integer limit) {

		QueryUtenteByMatricolaUsernameNomeCognomeTipoAccount q = new QueryUtenteByMatricolaUsernameNomeCognomeTipoAccount(session);

		q.setMatricola(matricola);
		q.setUsername(username);
		q.setNome(nome);
		q.setCognome(cognome);
		q.setTipoAccount(tipoAccount);
		q.setOffset(offset);
		q.setLimit(limit);
		
		return q.query();
	}

	@Override
	public QueryResult<Utente> list(
			int first, 
			int pageSize,
			String sortCriteria, 
			SortDirection sortDirection,
			Map<String, String> filters) {

		QueryUtenteByMatricolaUsernameNomeCognomeTipoAccount q = new QueryUtenteByMatricolaUsernameNomeCognomeTipoAccount(session);

		Integer matricola = null;
		if(filters.get("matricola") != null)
			matricola = Integer.decode(filters.get("matricola"));
		
		String username = filters.get("username");
		String nome = filters.get("nome");
		String cognome = filters.get("cognome");
		String tipoAccount = filters.get("tipoAccount");
		
		q.setMatricola(matricola);
		q.setUsername(username);
		q.setNome(nome);
		q.setCognome(cognome);
		q.setTipoAccount(tipoAccount);
		
		q.setOffset(first);
		q.setLimit(pageSize);
		
		q.setSortCriteria(sortCriteria);
		q.setSortDirection(sortDirection);
		
		return q.query();
	}
	
	@Override
	public Utente retrieveByMatricola(Integer matricola) {

		Utente utente = (Utente)session.get(Utente.class, matricola);
		logger.debug("Utente found: " + utente);
		
		return utente;
	}

	@Override
	public Utente retrieveByUsername(String username) {

		String hql = 
				"from Utente ute " +
				"where ute.username = :username";
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		Utente utente = (Utente)query.uniqueResult();
		logger.debug("Utente found: " + utente);
		
		return utente;
	}

	@Override
	public void deleteByMatricola(Integer matricola) {
		
		Utente utente = retrieveByMatricola(matricola);
		if(utente == null) {
			String message = String.format("It has not been possible to retrieve specified user: %d", matricola);
			logger.info(message);
			throw new NotFoundException(message);
		}
		
		session.delete(utente);
	}

	@Override
	public Utente create(
			Integer matricola,
			String username,
			String password,
			String nome, 
			String cognome, 
			String tipoAccount) {
		
		Utente utente = new Utente();
		
		utente.setMatricola(matricola);
		utente.setUsername(username);
		utente.setNome(nome);
		utente.setCognome(cognome);
		utente.setTipoAccount(tipoAccount);
		
		String salt = generateSalt();
		String digest = generateDigest(password, salt, HASH_ITERATIONS);

		utente.setDigest(digest);
		utente.setSalt(salt);
		utente.setIterations(HASH_ITERATIONS);
		
		session.save(utente);
		
		return utente;
	}

	@Override
	public Utente update(
			Integer matricola, 
			String username,
			String password,
			String nome, 
			String cognome, 
			String tipoAccount) {

		Utente utente = retrieveByMatricola(matricola);
		if(utente == null) {
			String message = String.format("It has not been possible to retrieve specified user: %d", matricola);
			logger.info(message);
			throw new NotFoundException(message);
		}
		
		utente.setUsername(username);
		utente.setNome(nome);
		utente.setCognome(cognome);
		utente.setTipoAccount(tipoAccount);
		
		if(password != null && password.trim().length() > 0) {
			String salt = generateSalt();
			String digest = generateDigest(password, salt, HASH_ITERATIONS);
	
			utente.setDigest(digest);
			utente.setSalt(salt);
			utente.setIterations(HASH_ITERATIONS);
		}
		
		session.update(utente);
		
		return utente;
	}
	
	
	private String generateSalt() {
		
		SecureRandomNumberGenerator srng = new SecureRandomNumberGenerator();
		String salt = srng.nextBytes(64).toHex();

		return salt;
	}
	
	
	private String generateDigest(String password, String salt, int iterations) {
		
		String hashed = new SimpleHash("SHA-512", password, salt, iterations).toHex();
		return hashed;		
	}
}