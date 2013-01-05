package it.dariofabbri.controlloaccessirai.jsf.managedbean;

import it.dariofabbri.controlloaccessirai.model.Utente;
import it.dariofabbri.controlloaccessirai.service.QueryResult;
import it.dariofabbri.controlloaccessirai.service.ServiceFactory;
import it.dariofabbri.controlloaccessirai.service.SortDirection;
import it.dariofabbri.controlloaccessirai.service.utente.UtenteService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@ManagedBean
@SessionScoped
public class UserListBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private LazyDataModel<Utente> model;
	private Utente selected;
	
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
}
