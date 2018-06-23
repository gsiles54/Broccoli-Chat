package com.cadena;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import com.Cliente.EntradaSalida;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.Sala;

public class ClienteSaliendo extends ChainCliente {
	ArrayList<String> copiaClientesEnLobby;
	DefaultListModel<String> modeloListaClientes;
	ArrayList<Sala> copiaSalasDisponibles;
	DefaultListModel<String> modelosSalasDisponibles;
	EntradaSalida entradaSalida;
	

	
	public ClienteSaliendo(ArrayList<String> copiaClientesEnLobby, DefaultListModel<String> modeloListaClientes,
			ArrayList<Sala> copiaSalasDisponibles, DefaultListModel<String> modeloListaSalas,
			EntradaSalida entradaSalida) {
		super();
		this.copiaClientesEnLobby = copiaClientesEnLobby;
		this.modeloListaClientes = modeloListaClientes;
		this.copiaSalasDisponibles = copiaSalasDisponibles;
		this.modelosSalasDisponibles = modeloListaSalas;
		this.entradaSalida = entradaSalida;
	}

	


	@Override
	public void manejarPeticion(Mensaje mensaje) {
		
			if(mensaje.getComando().equals(Comandos.ClienteSaliendo)) {
				System.out.println("ClienteSaliendo recibio: "+ mensaje.getComando());
				String nombreClienteSaliente=mensaje.getInformacion();
				copiaClientesEnLobby.remove(nombreClienteSaliente);
				modeloListaClientes.removeElement(nombreClienteSaliente);
				
				buscarYEliminarClienteDeSalas(nombreClienteSaliente);
				
				
			}else {System.out.println("ClienteSaliendo recibio: "+mensaje.getComando()+"\nAgregar mas comandos");}
	}
	private void buscarYEliminarClienteDeSalas(String nombreClienteSaliente) {
			for(Sala s: copiaSalasDisponibles) {
				s.sacarCliente(nombreClienteSaliente);
				if(s.getCantidadConectados()<=1) {
					copiaSalasDisponibles.remove(s);
					modelosSalasDisponibles.removeElement(s.getNombreSala());
					//Informar a todos que la sala se elimino por medio de un comando.
				}
			}
	}

}
