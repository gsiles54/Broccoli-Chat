package com.salas;

import java.util.ArrayList;

import com.vista.GUI_Lobby;
import com.vista.GUI_Sala;

public class Sala {
	
	ArrayList<String> clientesEnSala;
	String nombre; //se lo puede usar como hashTag.
	boolean esPrivada=false; //Todas las salas son publicas por defecto
	GUI_Sala salaGUI; //¿?
	GUI_Lobby lobby;
	Integer salaID;
	
	public Sala(Integer salaID,String nombre, boolean esPrivada){
		this.nombre=nombre;
		this.esPrivada=esPrivada;
		this.salaID=salaID;
		clientesEnSala= new ArrayList<String>();
	}
	public Sala(Integer salaID,String nombre, boolean esPrivada,GUI_Sala salaGUI){
		this.nombre=nombre;
		this.esPrivada=esPrivada;
		this.salaID=salaID;
		this.salaGUI=salaGUI;
		clientesEnSala= new ArrayList<String>();
	}


	public String getNombre() {
		return nombre;
	}

	public void setEsPrivada(boolean esPrivada) {
		this.esPrivada = esPrivada;
	}
	
	public void meterCliente(String cliente) {
		if(!clientesEnSala.contains(cliente)) {
			clientesEnSala.add(cliente);
		}
	}
	
	public void sacarCliente(String cliente) {
		if(clientesEnSala.contains(cliente)) {
			clientesEnSala.remove(cliente);
		}
	}

	public Integer getSalaID() {
	
		return salaID;
	}
	
	
	public GUI_Sala getSalaGui() {
		return salaGUI;
	}
	
}
