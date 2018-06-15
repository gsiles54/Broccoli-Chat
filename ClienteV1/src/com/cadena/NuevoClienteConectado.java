package com.cadena;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.Sala;
import com.vista.GUI_Lobby;

public class NuevoClienteConectado extends ChainCliente {
	
	GUI_Lobby lobbyGui;
	DefaultListModel<String> modeloLista;
	ArrayList<String> copiaClientesEnLobby; 


	public NuevoClienteConectado(GUI_Lobby lobbyGui, DefaultListModel<String> modeloLista, ArrayList<String> _copiaClientesEnLobby) {
		super();
		this.lobbyGui = lobbyGui;
		copiaClientesEnLobby=_copiaClientesEnLobby;
		this.modeloLista = modeloLista;
	}


	@Override
	public void manejarPeticion(Mensaje mensaje) {
		
		if(mensaje.getComando().equals(Comandos.ClienteNuevo)) {
			String usuarioEntrante= new String(mensaje.getInformacion());
			copiaClientesEnLobby.add(usuarioEntrante);
			modeloLista.addElement(usuarioEntrante);
			lobbyGui.getListaClientesConectados().setModel(modeloLista);
		}
		else {
			siguiente.manejarPeticion(mensaje);
		}
	}
}
