package com.Chain;

import java.util.List;

import com.cliente.Cliente;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.sala.Sala;
import com.servidor.ControladorServidor;

public class AgregarClienteASala extends Chain{

	@Override
	public void manejarPeticion(Mensaje mensaje) {
		// TODO Auto-generated method stub
		if (mensaje.getComando().equals(Comandos.InvitacionASalaPublicaAceptada)||mensaje.getComando().equals(Comandos.InvitacionASalaPrivadaAceptada)) {
			cs = ControladorServidor.getInstance();
			salas = cs.getSalas();
			clientesEnLobby = cs.getClientesEnLobby();
			boolean existeSala=false;
			String[] valores = mensaje.getInformacion().split(";");
			String nombreCliente = valores[0];
			Integer idSala = Integer.valueOf(valores[1]);
			
			Cliente nuevoEnSala =null;
			Sala salaModificada=null;
			for(Cliente clienteActual :  clientesEnLobby) {
				if(clienteActual.getNombre().equals(nombreCliente)) {
					nuevoEnSala = clienteActual;
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
				infoNueva.append(";");
				List<Cliente> listaClientes = salaModificada.getClientesEnSala();
				for(Cliente clienteActual : listaClientes) {
					infoNueva.append(clienteActual.getNombre());
					infoNueva.append(";");
				}
				infoNueva.deleteCharAt(infoNueva.length()-1);
				salaModificada.enviarMensaje(new Mensaje(mensaje.getComando(),infoNueva.toString()));
			}else {
				//informar error
			}
			
		}
		else
		{	
			siguiente.manejarPeticion(mensaje);
		}
	}

}
