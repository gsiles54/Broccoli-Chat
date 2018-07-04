package com.cadena;

import java.util.ArrayList;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.Sala;
import com.vista.GUI_Lobby;

public class ClienteDejandoSala extends ChainCliente{
	GUI_Lobby lobbyGUI;
	ArrayList<Sala> salas;
	
	public ClienteDejandoSala(GUI_Lobby lobbyGui, ArrayList<Sala> copiaSalasDisponible){
		this.lobbyGUI = lobbyGui;
		this.salas = copiaSalasDisponible;
	}
	
	@Override
	public void manejarPeticion(Mensaje msj) {
		if(msj.getComando().equals(Comandos.ClienteDejandoSala)) {
			System.out.println("ClienteDejando sala Recibio: " + msj.getComando());
			String[] valores = msj.getInformacion().split(";");
			String clienteSaliendo = valores[0];
			String nombreSala = valores[1];
			Integer idSala = Integer.valueOf(valores[2]);
			
			for( int i = 0 , sizeLista = salas.size(); i <sizeLista ; i++){
				Sala salaActual = salas.get(i);
				
				if(idSala.equals(salaActual.getSalaID())){
					salaActual.sacarCliente(clienteSaliendo);
					
					if(salaActual.getCantidadConectados()==0){
						
						lobbyGUI.quitarSala(nombreSala);
						salas.remove(i);
					}
						
				}
			}
		}
		else {siguiente.manejarPeticion(msj);}
	}

}
