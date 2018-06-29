package com.cadena;

import java.util.ArrayList;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.Sala;
import com.vista.GUI_Lobby;

public class ActualizarSalas extends ChainCliente{

	private GUI_Lobby lobbyGui;
	private ArrayList<Sala> copiaSalas;
	public ActualizarSalas(GUI_Lobby lobbyGui, ArrayList<Sala> copiaSalasDisponibles) {
		this.lobbyGui = lobbyGui;
		this.copiaSalas = copiaSalasDisponibles;
	}
	@Override
	public void manejarPeticion(Mensaje msj) {
		if(msj.getComando().equals(Comandos.BorrarSalaGUI)){
			System.out.println("ActualizarSalas Recibio: " + msj.getComando());
			String nombreSala= msj.getInformacion();
			lobbyGui.quitarSala(nombreSala);
			
		}else{
			if(msj.getComando().equals(Comandos.AgregarSalaGUI)) {
			System.out.println("ActualizarSalas Recibio: " + msj.getComando());
			String[] infoDeSalas = msj.getInformacion().split("$");
			for(String infoSalaActual : infoDeSalas){
				String[] datosSala = infoSalaActual.split(";");
				Integer idSala = Integer.valueOf(datosSala[0]);
				String nombreSala = datosSala[1];
				boolean esPriv = datosSala[2].equals("esPriv")?true:false;
				Sala nuevaSala = new Sala (idSala,nombreSala,esPriv);
				for(int i = 3; i < datosSala.length ; i++){
					nuevaSala.meterCliente(datosSala[i]);
				}
				copiaSalas.add(nuevaSala);
				lobbyGui.agregarSala(nuevaSala.getNombreSala());
			}
				
						
		}else {siguiente.manejarPeticion(msj);}
			
			
		}
		
		
	}

}
