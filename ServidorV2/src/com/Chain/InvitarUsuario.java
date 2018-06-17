package com.Chain;

import java.util.List;

import com.cliente.Cliente;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;

import com.servidor.ControladorServidor;

public class InvitarUsuario extends Chain{

	@Override
	public void manejarPeticion(Mensaje mensaje) {
		// TODO Auto-generated method stub
		if (mensaje.getComando().equals(Comandos.InvitarUsuarioSalaPrivada)||mensaje.getComando().equals(Comandos.InvitarUsuarioSalaPublica)) {
			cs=ControladorServidor.getInstance();
			
			List<Cliente> clientes =cs.getClientesEnLobby();
			String[] valores = mensaje.getInformacion().split(";");
			String nombre = valores[0];

			for(Cliente c: clientes) {
				if(c.getNombre().equals(nombre)) {
					if(mensaje.getComando().equals(Comandos.InvitarUsuarioSalaPrivada)) {
						c.enviarMensaje(new Mensaje(Comandos.InvitacionASalaPrivada,mensaje.getInformacion()));
					}else {
						c.enviarMensaje(new Mensaje(Comandos.InvitacionASalaPublica,mensaje.getInformacion()));
					
					}
					break;
				}
					
			}
		}
		else
		{	
			System.out.println("Ultimo eslabon. El comando era: "+mensaje.getComando());
			System.out.println("Agregar mas manejadores");
		}
	}

}
