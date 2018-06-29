package com.cadena;

import java.util.ArrayList;


import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.vista.GUI_Lobby;

public class NuevoClienteConectado extends ChainCliente {
	
	GUI_Lobby lobbyGui;
	ArrayList<String> copiaClientesEnLobby; 


	public NuevoClienteConectado(GUI_Lobby lobbyGui, ArrayList<String> _copiaClientesEnLobby) {
		super();
		copiaClientesEnLobby=_copiaClientesEnLobby;
		this.lobbyGui = lobbyGui;
	}


	@Override
	public void manejarPeticion(Mensaje mensaje) {
		
		if(mensaje.getComando().equals(Comandos.ClienteNuevo)) {
			System.out.println("NuevoClienteConectado Recibio: " + mensaje.getComando());
			
			String usuarioEntrante= mensaje.getInformacion();
			copiaClientesEnLobby.add(usuarioEntrante);
			lobbyGui.agregarCliente(usuarioEntrante);
		}
		else {siguiente.manejarPeticion(mensaje);}
	}
	

}
