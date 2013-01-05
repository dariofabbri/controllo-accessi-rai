package it.dariofabbri.controlloaccessirai.service;

import org.primefaces.model.SortOrder;

public enum SortDirection {
	Ascending,
	Descending,
	None;
	
	public String toHql() {
		
		switch(this) {
			case Ascending:
				return "asc";
				
			case Descending:
				return "desc";
				
			default:
				return "";
		}
	}
	
	public static SortDirection fromSortOrder(SortOrder so) {
		
		switch(so) {
			case ASCENDING:
				return SortDirection.Ascending;
			
			case DESCENDING:
				return SortDirection.Descending;
				
			case UNSORTED:
				return SortDirection.None;
		}
		
		return null;
	}
}
