package it.dariofabbri.controlloaccessirai.jsf.managedbean;

import it.dariofabbri.controlloaccessirai.model.Utente;
import it.dariofabbri.controlloaccessirai.service.QueryResult;
import it.dariofabbri.controlloaccessirai.service.ServiceFactory;
import it.dariofabbri.controlloaccessirai.service.SortDirection;
import it.dariofabbri.controlloaccessirai.service.utente.UtenteService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ViewScoped
public class UserListBean implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(UserListBean.class);

	private static final long serialVersionUID = 1L;

	private LazyDataModel<Utente> model;
	private Utente selected;
	
	private String matricola;
	private String username;
	private String nome;
	private String cognome;
	private String tipoAccount;
	private String password;
	private String confirmPassword;
	
	public UserListBean() {
		
		model = new LazyDataModel<Utente>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Utente> load(
					int first, 
					int pageSize, 
					String sortField,
					SortOrder sortOrder, Map<String, String> filters) {

				UtenteService us = ServiceFactory.createUtenteService();
				QueryResult<Utente> result = us.list(
						first, 
						pageSize, 
						sortField, 
						SortDirection.fromSortOrder(sortOrder), 
						filters);
				
				this.setRowCount(result.getRecords());
				
				return result.getResults();
			}
			
			@Override
			public Object getRowKey(Utente utente) {
				return utente == null ? null : utente.getMatricola();
			}

			@Override
			public Utente getRowData(String rowKey) {
				
				UtenteService us = ServiceFactory.createUtenteService();
				Utente utente = us.retrieveByMatricola(Integer.decode(rowKey));
				return utente;
			}
		};
	}
	
	public String doCreate() {
		
		logger.debug("Inside create event action listener!");

		// Check if password matches confirmation field.
		//
		if(!password.equals(confirmPassword)) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, 
					"Le password differiscono", 
					"La password e la relativa conferma differiscono");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
		
		// Create the new user.
		//
		try {
			UtenteService us = ServiceFactory.createUtenteService();
			us.create(
					Integer.decode(matricola), 
					username, 
					password, 
					nome, 
					cognome, 
					tipoAccount);
			
			// Signal to modal dialog that everything went fine.
			///
			RequestContext.getCurrentInstance().addCallbackParam("ok", true);
			
			return "/private/utenti/list";
			
		} catch(Exception e) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, 
					"Errore di sistema", 
					"Si è verificato un errore in fase di creazione dell'utente");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		return null;
	}

	public LazyDataModel<Utente> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<Utente> model) {
		this.model = model;
	}

	public Utente getSelected() {
		return selected;
	}

	public void setSelected(Utente selected) {
		this.selected = selected;
	}

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
