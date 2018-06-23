package com.salas;

import java.util.ArrayList;

import com.vista.GUI_Lobby;
import com.vista.GUI_Sala;

public class Sala {
	
	ArrayList<String> clientesEnSala;
	String nombreSala; //se lo puede usar como hashTag.
	boolean esPrivada=false; //Todas las salas son publicas por defecto
	GUI_Sala salaGUI; //¿?
	GUI_Lobby lobby;
	Integer salaID;
	
	public Sala(Integer salaID,String nombre, boolean esPrivada){
		this.nombreSala=nombre;
		this.esPrivada=esPrivada;
		this.salaID=salaID;
		clientesEnSala= new ArrayList<String>();
	}
	public Sala(Integer salaID,String nombre, boolean esPrivada,GUI_Sala salaGUI){
		this.nombreSala=nombre;
		this.esPrivada=esPrivada;
		this.salaID=salaID;
		this.salaGUI=salaGUI;
		clientesEnSala= new ArrayList<String>();
	}


	public String getNombreSala() {
		return nombreSala;
	}

	public void setEsPrivada(boolean esPrivada) {
		this.esPrivada = esPrivada;
	}
	
	public boolean meterCliente(String cliente) {
		if(!clientesEnSala.contains(cliente)) {
			clientesEnSala.add(cliente);
			return true;
		}
		return false;
	}
	
	public void sacarCliente(String cliente) {
		if(clientesEnSala.contains(cliente)) {
			clientesEnSala.remove(cliente);
		}
	}

	public Integer getSalaID() {
	
		return salaID;
	}
	public boolean esPrivada() {
		return esPrivada;
	}
	
	public GUI_Sala getSalaGui() {
		return salaGUI;
	}
	
	public int getCantidadConectados() {return clientesEnSala.size();}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombreSala == null) ? 0 : nombreSala.hashCode());
		result = prime * result + ((salaID == null) ? 0 : salaID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sala other = (Sala) obj;
		if (nombreSala == null) {
			if (other.nombreSala != null)
				return false;
		} else if (!nombreSala.equals(other.nombreSala))
			return false;
		if (salaID == null) {
			if (other.salaID != null)
				return false;
		} else if (!salaID.equals(other.salaID))
			return false;
		return true;
	}
	
	
	
}
