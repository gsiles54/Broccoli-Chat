package com.Chain;

import java.util.ArrayList;

import com.cliente.Cliente;
import com.logs.LoggerCliente;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.sala.Sala;
import com.servidor.ControladorServidor;

public class CrearSala extends Chain{

	public CrearSala(ArrayList<Sala> _salas,  ArrayList<Cliente> _clientesEnLobby) {
		salas=_salas;
		clientesEnLobby=_clientesEnLobby;
		
	}

	@Override
	public void manejarPeticion(Mensaje mensaje) {
		if (mensaje.getComando().equals(Comandos.CrearSalaPublica)||mensaje.getComando().equals(Comandos.CrearSalaPrivada))
		{           
			boolean esPrivada = mensaje.getComando().equals(Comandos.CrearSalaPublica)?false:true;
			String nombre = mensaje.getInformacion();
			Cliente cliente = getClientePorNombre(mensaje.getEmisor());
			if(salaYaExistente(nombre)) {
				LoggerCliente.enviarLog("Se intenta crear una sala de nombre repetido: "+nombre);
				if(cliente!=null) {
						String error="Nombre de sala ya existente, elija otro nombre.";
						cliente.enviarMensaje(new Mensaje(Comandos.SalaNoCreadaNombreDuplicado,error));
					}
				return;
			}

			Sala nuevaSala=new Sala(nombre,esPrivada);
			
			nuevaSala.meterCliente(cliente);
			salas.add(nuevaSala);
			
			if(esPrivada==false) {
					
					if(esPrivada) {
						cliente.enviarMensaje(new Mensaje(Comandos.SalaPrivCreadaExitosamente,nombre+";"+nuevaSala.getSalaID()));
					}else {
						cliente.enviarMensaje(new Mensaje(Comandos.SalaPubCreadaExitosamente,nombre+";"+nuevaSala.getSalaID()));
					}
					
					
			}
			LoggerCliente.enviarLog("Sala creada Exitosamente: "+nombre);
		}
		else
		{
			siguiente.manejarPeticion(mensaje);
		}
	}
	
	private boolean salaYaExistente(String nombre) {
		for(Sala s: salas) {
			if(s.getSalaID().equals(nombre))
				return true;
		}
		return false;
	}
	
	private Cliente getClientePorNombre(String nombre) {
		for(Cliente c: clientesEnLobby) {
			if(c.getNombre().equals(nombre))
				return c;
		}
		return null;
	}

}