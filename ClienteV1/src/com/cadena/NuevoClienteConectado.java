package com.cadena;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.vista.GUI_Lobby;

public class NuevoClienteConectado extends ChainCliente {
	
	DefaultListModel<String> modeloListaClientes;
	ArrayList<String> copiaClientesEnLobby; 


	public NuevoClienteConectado(DefaultListModel<String> modeloLista, ArrayList<String> _copiaClientesEnLobby) {
		super();
		copiaClientesEnLobby=_copiaClientesEnLobby;
		this.modeloListaClientes = modeloLista;
	}


	@Override
	public void manejarPeticion(Mensaje mensaje) {
		
		if(mensaje.getComando().equals(Comandos.ClienteNuevo)) {
			System.out.println("NuevoClienteConectado Recibio: " + mensaje.getComando());
			
			String usuarioEntrante= mensaje.getInformacion();
			copiaClientesEnLobby.add(usuarioEntrante);
			modeloListaClientes.addElement(usuarioEntrante);
		}
		else {siguiente.manejarPeticion(mensaje);}
	}
	

}
