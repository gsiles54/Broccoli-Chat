package com.Chain;

import java.util.ArrayList;
import java.util.List;

import com.cliente.Cliente;
import com.logs.LoggerCliente;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.sala.Sala;

public class AgregarClienteASala extends Chain{
	
	ArrayList<Sala> salas;
	ArrayList<Cliente> clientesEnLobby;
	
	public AgregarClienteASala(ArrayList<Sala> salas,ArrayList<Cliente> clientesEnLobby) {
		this.salas=salas;
		this.clientesEnLobby=clientesEnLobby;
	}

	@Override
	public void manejarPeticion(Mensaje mensaje) {
		
		if (mensaje.getComando().equals(Comandos.InvitacionASalaPublicaAceptada)||mensaje.getComando().equals(Comandos.InvitacionASalaPrivadaAceptada)) {
			System.out.println("AgregarCLienteASala Recibio: "+mensaje.getComando());
			boolean existeSala=false;
			String[] valores = mensaje.getInformacion().split(";");
			String nombreCliente = valores[0];
			Integer idSala = Integer.valueOf(valores[1]);
			
			Cliente nuevoEnSala =null;
			Sala salaModificada=null;
			for(Cliente clienteActual :  clientesEnLobby) {
				if(clienteActual.getNombre().equals(nombreCliente)) {
					nuevoEnSala = clienteActual;
					break;
				}
			}
			
			for(Sala salaActual : salas) {
			if(salaActual.getSalaID().equals(idSala)) {
				salaModificada=salaActual;
				salaModificada.meterCliente(nuevoEnSala);
				existeSala=true;
				}		
			}
			
			if(existeSala) {
				StringBuilder infoNueva = new StringBuilder();
				infoNueva.append(mensaje.getInformacion());
				infoNueva.append(";");
				infoNueva.append(salaModificada.getNombre());
				
				List<Cliente> listaClientes = salaModificada.getClientesEnSala();
				for(Cliente clienteActual : listaClientes) {
						infoNueva.append(";");
						infoNueva.append(clienteActual.getNombre());
						
					
					
				}

				salaModificada.enviarMensaje(new Mensaje(mensaje.getComando(),infoNueva.toString()));
			}else {
				LoggerCliente.enviarLog("Problema en Servidor:AgregarCLiente");
			}
			
		}
		else{siguiente.manejarPeticion(mensaje);}
	}

}
