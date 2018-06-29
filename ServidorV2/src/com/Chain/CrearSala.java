package com.Chain;

import java.util.ArrayList;

import com.cliente.Cliente;
import com.logs.LoggerCliente;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.sala.Sala;


public class CrearSala extends Chain{

	private ArrayList<Cliente> clientesEnLobby;
	private ArrayList<Sala> salas;

	public CrearSala(ArrayList<Sala> _salas,  ArrayList<Cliente> _clientesEnLobby) {
		salas=_salas;
		clientesEnLobby=_clientesEnLobby;
		
	}

	@Override
	public void manejarPeticion(Mensaje mensaje) {
		if (mensaje.getComando().equals(Comandos.CrearSalaPublica)||mensaje.getComando().equals(Comandos.CrearSalaPrivada)){
			
			System.out.println("CrearSala Recibio: "+mensaje.getComando());
			boolean esPrivada = mensaje.getComando().equals(Comandos.CrearSalaPublica)?false:true;
			String nombreSala = mensaje.getInformacion();
			Cliente clienteEmisor = getClientePorNombre(mensaje.getEmisor());
			if(nombreSalaYaExistente(nombreSala)) {
				LoggerCliente.enviarLog("Se intenta crear una sala de nombre repetido: "+nombreSala);
				if(clienteEmisor!=null) {
						String error="Nombre de sala ya existente, elija otro nombre.";
						clienteEmisor.enviarMensaje(new Mensaje(Comandos.SalaNoCreadaNombreDuplicado,error));
					}
				return;
			}

			Sala nuevaSala=new Sala(nombreSala,esPrivada);
			
			nuevaSala.meterCliente(clienteEmisor);
			salas.add(nuevaSala);
			StringBuilder informacion = new StringBuilder();
			informacion.append(nombreSala);
			informacion.append(";");
			informacion.append(nuevaSala.getSalaID());
			informacion.append(";");
			informacion.append(clienteEmisor.getNombre());
			if(esPrivada) 
				for(Cliente clienteActual : clientesEnLobby){
					clienteActual.enviarMensaje(new Mensaje(Comandos.SalaPrivCreadaExitosamente,informacion.toString()));
				}
			else 
				for(Cliente clienteActual : clientesEnLobby){
					clienteActual.enviarMensaje(new Mensaje(Comandos.SalaPubCreadaExitosamente,informacion.toString()));
				}
					
			LoggerCliente.enviarLog("Sala creada Exitosamente: "+nombreSala);
		}
		
		else{siguiente.manejarPeticion(mensaje);}
	}
	
	private boolean nombreSalaYaExistente(String nombre) {
		for(Sala s: salas) {
			if(s.getNombre().equals(nombre))
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