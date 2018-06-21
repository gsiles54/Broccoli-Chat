package com.cadena;


import java.util.ArrayList;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.HiloOutputSala;
import com.salas.Sala;
import com.vista.GUI_Sala;

public class CrearSala extends ChainCliente{
	
	String nombreCliente;
	ArrayList<Sala> copiaSalasDisponibles;
	
	public CrearSala(String nombreCliente, ArrayList<Sala> copiaSalasDisponibles) {
		this.nombreCliente=nombreCliente;
		this.copiaSalasDisponibles=copiaSalasDisponibles;
	}

	@Override
	public void manejarPeticion(Mensaje mensaje) {
		
		if(mensaje.getComando().equals(Comandos.SalaPrivCreadaExitosamente)||mensaje.getComando().equals(Comandos.SalaPubCreadaExitosamente)) {
			GUI_Sala guiSala = new GUI_Sala();
			String valores [] = mensaje.getInformacion().split(";");
			String nombre = valores[0];
			Integer idSala = Integer.valueOf(valores[1]);

			guiSala.setVisible(true);
			guiSala.agregarCliente(nombreCliente);
			boolean esPrivada = mensaje.getComando().equals(Comandos.SalaPrivCreadaExitosamente)?true:false;
			Sala nuevaSala = new Sala(idSala,nombre,esPrivada,guiSala);
			copiaSalasDisponibles.add(nuevaSala);
			guiSala.setSala(nuevaSala);
			nuevaSala.meterCliente(nombreCliente);
			HiloOutputSala hiloSala = new HiloOutputSala(nombreCliente,guiSala,nuevaSala);
			Thread thSala = new Thread(hiloSala);
			thSala.start();
		}
		else {siguiente.manejarPeticion(mensaje);}
	}

}
