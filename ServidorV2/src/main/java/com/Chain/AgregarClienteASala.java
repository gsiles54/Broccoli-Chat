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
	boolean existeSala=false;
	Cliente nuevoEnSala =null;
	Sala salaModificada=null;
	
	public AgregarClienteASala(ArrayList<Sala> salas,ArrayList<Cliente> clientesEnLobby) {
		this.salas=salas;
		this.clientesEnLobby=clientesEnLobby;
	}

	@Override
	public void manejarPeticion(Mensaje mensaje) {
		
		if (mensaje.getComando().equals(Comandos.InvitacionASalaPublicaAceptada)||mensaje.getComando().equals(Comandos.InvitacionASalaPrivadaAceptada)) {
			System.out.println("AgregarCLienteASala Recibio: "+mensaje.getComando());
			
			String[] valores = mensaje.getInformacion().split(";");
			String nombreCliente = valores[0];
		
			if(!existeClienteEnSala(valores)) {
				
			
			for(Cliente clienteActual :  clientesEnLobby) {
				if(clienteActual.getNombre().equals(nombreCliente)) {
					nuevoEnSala = clienteActual;
					break;
				}
			}
			
			buscarSala(valores);
			
			if(existeSala) {
				StringBuilder infoNueva = new StringBuilder();
				infoNueva.append(nombreCliente);
				infoNueva.append(";");
				infoNueva.append(salaModificada.getSalaID());
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
			}}
			
		}
		else{siguiente.manejarPeticion(mensaje);}
	}

	
	private boolean existeClienteEnSala(String[] valores) {
		String nombreCliente = valores[0];
		for(Sala salaActual : salas) {
			for(Cliente clienteActual : salaActual.getClientesEnSala()) {
				if(clienteActual.getNombre().equals(nombreCliente)) {
					if(valores.length>2&&salaActual.getNombre().equals(valores[2])) {
						return true;
					}else {
						if(salaActual.getSalaID().equals(valores[1]))
							return true;
					}
					
				}
					
			}
		}
		return false;
	}

	public void buscarSala(String[] valores) {
		int len = valores.length;
		
		
		
		if(len>2) {
			String nombreSala = valores[2];
		
				for(Sala salaActual : salas) {
					if(salaActual.getNombre().equals(nombreSala)) {
						salaModificada=salaActual;
						salaModificada.meterCliente(nuevoEnSala);
						existeSala=true;
					}
				}
		}else {
			Integer idSala = Integer.valueOf(valores[1]);
			for(Sala salaActual : salas) {
				if(salaActual.getSalaID().equals(idSala)) {
					salaModificada=salaActual;
					salaModificada.meterCliente(nuevoEnSala);
					existeSala=true;
					}		
				}
		}
	}
}
