package com.Chain;

import java.util.ArrayList;

import com.cliente.Cliente;
import com.mensajes.Comandos;
import com.mensajes.EntradaSalida;
import com.mensajes.Mensaje;


public class RefrescarListaClientes extends Chain{

	private ArrayList<Cliente> clientesEnLobby;

	public RefrescarListaClientes(ArrayList<Cliente> clientesEnLobby) {
		this.clientesEnLobby = clientesEnLobby;
	}
	
	@Override
	public void manejarPeticion(Mensaje mensaje) {
		if (mensaje.getComando().equals(Comandos.RefrescarClientes)) {
			System.out.println("Refrescar clientes recibio: "+mensaje.getComando());
			String nombreClienteSolicitante = mensaje.getInformacion();
			StringBuilder clientesConectados = new StringBuilder();
			Cliente clienteReceptor=null;
			for (Cliente clienteActual : clientesEnLobby) {
				clientesConectados.append(clienteActual.getNombre());
				clientesConectados.append(";");
				if(nombreClienteSolicitante.equals(clienteActual.getNombre()))
					clienteReceptor = clienteActual;
			}
			
			clientesConectados.deleteCharAt(clientesConectados.length()-1);
			clienteReceptor.enviarMensaje(new Mensaje(Comandos.RefrescarClientes,clientesConectados.toString()));
			
		} else {
			siguiente.manejarPeticion(mensaje);
		}
	}
}
