package com.Chain;


import java.util.ArrayList;

import com.cliente.Cliente;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.sala.Sala;

public class CrearMP extends Chain{

	
	private ArrayList<Cliente> clientesEnLobby;
	private ArrayList<Sala> salas;

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
			
			StringBuilder informacion = new StringBuilder();
			
			informacion.append(nuevaSala.getNombre());
			informacion.append(";");
			informacion.append(nuevaSala.getSalaID());
			informacion.append(";");
			informacion.append(nombreEmisor);
			informacion.append(";");
			informacion.append(nombreDestinatario);
			nuevaSala.enviarMensaje(new Mensaje(Comandos.SalaPrivCreadaExitosamente,informacion.toString()));
	
			}
		else
		{
			siguiente.manejarPeticion(mensaje);
		}
		
	}

}
