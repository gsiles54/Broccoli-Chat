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


	public String getNombre() {
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
	
}
