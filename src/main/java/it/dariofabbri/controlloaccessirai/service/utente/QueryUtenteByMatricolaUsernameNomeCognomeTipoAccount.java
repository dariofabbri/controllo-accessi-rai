package it.dariofabbri.controlloaccessirai.service.utente;

import it.dariofabbri.controlloaccessirai.model.Utente;
import it.dariofabbri.controlloaccessirai.service.Query;

import org.hibernate.Session;

public class QueryUtenteByMatricolaUsernameNomeCognomeTipoAccount extends Query<Utente> {

	private Integer matricola;
	private String username;
	private String nome;
	private String cognome;
	private String tipoAccount;
	
	public QueryUtenteByMatricolaUsernameNomeCognomeTipoAccount(Session session) {
		
		super(session);
		
		sortCriteria = "matricola";
	}

	public Integer getMatricola() {
		return matricola;
	}

	public void setMatricola(Integer matricola) {
		this.matricola = matricola;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getTipoAccount() {
		return tipoAccount;
	}

	public void setTipoAccount(String tipoAccount) {
		this.tipoAccount = tipoAccount;
	}

	@Override
	protected boolean checkQueryArguments() {

		return true;
	}

	@Override
	protected String getCountHql() {

		String hql = 
				"select count(*) " +
				"from Utente ute " +
				"where 1 = 1 ";
		
		if(matricola != null)
			hql += "and ute.matricola = :matricola ";
		
		if(username != null)
			hql += "and upper(ute.username) like :username ";
		
		if(nome != null)
			hql += "and upper(ute.nome) like :nome ";
		
		if(cognome != null)
			hql += "and upper(ute.cognome) like :cognome ";
		
		if(tipoAccount != null)
			hql += "and upper(ute.tipoAccount) like :tipoAccount ";

		return hql;
	}

	@Override
	protected String getQueryHql() {

		String hql = 
				"from Utente ute " +
				"where 1 = 1 ";
		
		if(matricola != null)
			hql += "and ute.matricola = :matricola ";
		
		if(username != null)
			hql += "and upper(ute.username) like :username ";
		
		if(nome != null)
			hql += "and upper(ute.nome) like :nome ";
		
		if(cognome != null)
			hql += "and upper(ute.cognome) like :cognome ";
		
		if(tipoAccount != null)
			hql += "and upper(ute.tipoAccount) like :tipoAccount ";

		if(sortCriteria != null) {
			hql += "order by ute." + sortCriteria + " ";
			
			if(sortDirection != null)
				hql += sortDirection.toHql();
		} 
		
		return hql;
	}

	@Override
	protected void setQueryArguments(org.hibernate.Query q) {

		String[] named_params = q.getNamedParameters();
		for (int i = 0; i < named_params.length; ++i) {
			String param = named_params[i];

			if (param.equals("matricola"))
				q.setParameter("matricola", matricola);
			
			else if (param.equals("username"))
				q.setParameter("username", "%" + username.toUpperCase() + "%");

			else if (param.equals("nome"))
				q.setParameter("nome", "%" + nome.toUpperCase() + "%");
			
			else if (param.equals("cognome"))
				q.setParameter("cognome", "%" + cognome.toUpperCase() + "%");
			
			else if (param.equals("tipoAccount"))
				q.setParameter("tipoAccount",  "%" + tipoAccount.toUpperCase() + "%");
		}
	}
}