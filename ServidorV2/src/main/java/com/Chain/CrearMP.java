package com.Chain;


import java.util.ArrayList;

import com.cliente.Cliente;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.sala.Sala;

public class CrearMP extends Chain{

	
	private ArrayList<Cliente> clientesEnLobby;
	private ArrayList<Sala> salas;
	private Sala salaExistente=null;
	private Cliente clienteEmisor;
	
	public CrearMP(ArrayList<Sala> salas, ArrayList<Cliente> clientesEnLobby) {
		this.salas =salas;
		this.clientesEnLobby = clientesEnLobby;
	}

	@Override
	public void manejarPeticion(Mensaje mensaje) {
		if (mensaje.getComando().equals(Comandos.CrearMP)){
			System.out.println("CrearMP Recibio: "+mensaje.getComando());
			String valores[] = mensaje.getInformacion().split(";");
			String nombreEmisor = valores[0];
			String nombreDestinatario = valores[1];
			
			if(!existeConversacion(nombreEmisor, nombreDestinatario)){
				Sala nuevaSala = new Sala("Conversacion",true);
			salas.add(nuevaSala);
			Cliente emisor = null;
			Cliente destinatario = null;
			for(Cliente clienteActual:clientesEnLobby){
				if(clienteActual.getNombre().equals(nombreEmisor))
					emisor = clienteActual;
				if(clienteActual.getNombre().equals(nombreDestinatario))
					destinatario = clienteActual;
			}
			nuevaSala.meterCliente(emisor);
			nuevaSala.meterCliente(destinatario);
			nuevaSala.setConversacion(true);
			StringBuilder informacion = new StringBuilder();
			
			informacion.append(nuevaSala.getNombre());
			informacion.append(";");
			informacion.append(nuevaSala.getSalaID());
			informacion.append(";");
			informacion.append(nombreEmisor);
			informacion.append(";");
			informacion.append(nombreDestinatario);
			nuevaSala.enviarMensaje(new Mensaje(Comandos.SalaPrivCreadaExitosamente,informacion.toString()));
	
			}else {
				clienteEmisor.enviarMensaje(new Mensaje(Comandos.ExisteSala,salaExistente.getSalaID()));
			}
			
			
			}
		else
		{
			siguiente.manejarPeticion(mensaje);
		}
		
	}
	
	public boolean existeConversacion(String emisor,String destinatario){
		boolean existeEmisor=false;
		boolean existeDestinatario=false;
		for(Sala salaActual:salas){
			if(salaActual.isConversacion())
			for(Cliente clienteActual:salaActual.getClientesEnSala()){
				if(clienteActual.getNombre().equals(emisor)) {
					clienteEmisor = clienteActual;
					existeEmisor=true;
				}
					
				if(clienteActual.getNombre().equals(destinatario))
					existeDestinatario=true;
			}
			if(existeEmisor&&existeDestinatario) {
				salaExistente = salaActual;
				return true;
			}
				
				
			existeEmisor=false;
			existeDestinatario=false;
		}
		return false;
	}

}
