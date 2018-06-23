package com.Chain;

import java.util.ArrayList;

import com.cliente.Cliente;
import com.logs.LoggerCliente;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.sala.Sala;



public class DesconectarCliente extends Chain{

	
	private ArrayList<Cliente> clientesEnLobby;
	private ArrayList<Sala> salas;

	public DesconectarCliente(ArrayList<Sala> _salas,  ArrayList<Cliente> _clientesEnLobby) {
		salas=_salas;
		clientesEnLobby=_clientesEnLobby;
	}
	
	@Override
	public void manejarPeticion(Mensaje mensaje) {
		if (mensaje.getComando().equals(Comandos.LOGOUT)){
			System.out.println("DesconectarCliente Recibio: "+mensaje.getComando());
			Cliente clienteSaliente=getClientePorNombre(mensaje.getInformacion());
			sacarClienteDeSalas(clienteSaliente);
			clientesEnLobby.remove(clienteSaliente);
			aTodos_clienteSaliendo(clienteSaliente.getNombre());
			clienteSaliente.cerrarSockets();
			LoggerCliente.enviarLog("Cliente: "+clienteSaliente.getNombre()+" solicitó desconectarse del chat");
			LoggerCliente.enviarLog("Clientes conectados actualmente: "+clientesEnLobby.size());
		}
		else
		{
			siguiente.manejarPeticion(mensaje);
		}
	}
	
	private void aTodos_clienteSaliendo(String nombreSaliente) {
		for(Cliente c: clientesEnLobby) {
			if(!c.getNombre().equals(nombreSaliente)) {
				c.enviarMensaje(new Mensaje(Comandos.ClienteSaliendo,nombreSaliente));
			}
				
		}
	}
	
	private void sacarClienteDeSalas(Cliente saliente) {
		int cont=0;
		for(Sala s: salas) {
			cont+=s.sacarCliente(saliente);
			//Verificar si era el ultimo cliente en la sala tambien, si era el ultimo en la sala, borrar sala!!!
		}
		LoggerCliente.enviarLog("El cliente: "+saliente.getNombre()+"se encontraba conectado a: "+cont+" salas.");
	}
	
	private Cliente getClientePorNombre(String nombre) {
		for(Cliente c: clientesEnLobby) {
			if(c.getNombre().equals(nombre))
				return c;
		}
		return null;
	}

}
