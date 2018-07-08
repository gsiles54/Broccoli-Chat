package com.mensajes;

import java.io.Serializable;



public class Mensaje implements Serializable {
	
	private static final long serialVersionUID = 2928832563874494113L;
	private String comando;
	private String informacion; // es el dato siendo enviado. En principio, solo texto.
	private Integer salaID;
	private String emisor;
	/**Usado para: Enviar chat a sala  El cliente no deberia generar ID SALA !*/
	
	public Mensaje(String _comando,String _informacion,Integer salaID, String emisor) {
		comando = _comando;
		informacion = _informacion;
		this.salaID=salaID;
		this.emisor=emisor;
	}
	
	/**Usado para:  */
	public Mensaje(String comando, String informacion) {
		this.comando = comando;
		this.informacion = informacion;
		
	}
	public Mensaje(String comando, Integer salaID) {
		this.comando=comando;
		this.salaID=salaID;
	}
	
	public String getComando() {
		return comando;
	}
	
		
	public String getInformacion() {
		return informacion;
	}
	
	
	
	@Override
	public String toString() {
		
			return this.informacion;
		
	}

	public Integer getSalaID() {
		return salaID;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	
	
}
