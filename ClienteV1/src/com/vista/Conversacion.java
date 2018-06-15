package com.vista;

import com.salas.Sala;

public class Conversacion {
	
	GUI_Sala Gui;
	Sala sala;
	
	public Conversacion(GUI_Sala gui, Sala _sala) {
		Gui=gui;
		sala=_sala;
	}
	
	public void meterCliente(String entrante) {
		sala.meterCliente(entrante);
	}
	
	
}
