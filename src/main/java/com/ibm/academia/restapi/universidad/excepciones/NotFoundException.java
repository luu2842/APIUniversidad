package com.ibm.academia.restapi.universidad.excepciones;

public class NotFoundException extends RuntimeException
{

	public NotFoundException(String mensaje) {
		super(mensaje);
	}
	
	private static final long serialVersionUID = 6407196044322865851L;
	
}
