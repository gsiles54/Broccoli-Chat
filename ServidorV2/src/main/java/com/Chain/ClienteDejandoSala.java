package com.Chain;

import java.util.ArrayList;

import com.cliente.Cliente;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.sala.Sala;

public class ClienteDejandoSala extends Chain{
	
	ArrayList<Sala> salas;
	ArrayList<Cliente> clientesEnLobby;
	public ClienteDejandoSala(ArrayList<Sala> salas,ArrayList<Cliente> clientesEnLobby){
		this.salas = salas;
		this.clientesEnLobby = clientesEnLobby;
	}
	@Override
	public void manejarPeticion(Mensaje mensaje) {
		if (mensaje.getComando().equals(Comandos.ClienteDejandoSala)) {
			System.out.println("DejandoSala Recibio: "+mensaje.getComando());
			String[] valores = mensaje.getInformacion().split(";");
			String nombreCliente = valores[0];
			Integer idSala = Integer.valueOf(valores[2]);
			for (int i = 0 , sizeLista = salas.size(); i < sizeLista ; i++) {
				Sala salaActual = salas.get(i);
				if (idSala.equals(salaActual.getSalaID())){
					salaActual.enviarMensaje(mensaje);
					
					salaActual.sacarCliente(nombreCliente);
					if(salaActual.getClientesEnSala().isEmpty()){
						
						for(Cliente clienteActual : clientesEnLobby){
							
							clienteActual.enviarMensaje(new Mensaje (Comandos.BorrarSalaGUI,salaActual.getNombre()));
						}
						salas.remove(i);
					}
					break;
				}			
			}

		} else {
			siguiente.manejarPeticion(mensaje);
		}
	}

}
