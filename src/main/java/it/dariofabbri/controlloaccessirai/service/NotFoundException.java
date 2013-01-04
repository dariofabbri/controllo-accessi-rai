package it.dariofabbri.controlloaccessirai.service;

public class NotFoundException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public NotFoundException() {
		super();
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Throwable t) {
		super(message, t);
	}
}
