package com.mensajes;

import java.io.Serializable;



public class Mensaje implements Serializable {
	
	private static final long serialVersionUID = 2928832563874494113L;
	String comando;
	String informacion; // es el dato siendo enviado. En principio, solo texto.
	Integer salaID;
	String emisor;

	/**Usado para: Enviar chat a sala  El cliente no deberia generar ID SALA !*/
	
	public Mensaje(String _comando,String _informacion, Integer salaID) {
		comando = _comando;
		informacion = _informacion;
		this.salaID=salaID;
	}

	/**Usado para: logout */
	public Mensaje(String comando, String informacion) {
		this.comando = comando;
		this.informacion = informacion;
		this.salaID=-1;
	}
	
	public Mensaje(String _comando,String _informacion,String emisor) {
		comando = _comando;
		informacion = _informacion;
		this.salaID=-1;
		this.emisor=emisor;
	}
	
	public String getComando() {
		return comando;
	}
	
	
	public String getInformacion() {
		return informacion;
	}
	
	public Integer getIDSala() {
		return salaID;
	}
	

	@Override
	public String toString() {
		
			return this.informacion;
		
	}
	
	
}
