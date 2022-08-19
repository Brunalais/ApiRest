package com.aulas.controllers.exception;

public class EmailInvalidoExeception extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public EmailInvalidoExeception(String msg) {
			super(msg); 
	}
		
}
