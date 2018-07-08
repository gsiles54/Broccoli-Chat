package com.Chain;

import java.util.ArrayList;


import com.cliente.Cliente;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;

public class InvitarUsuario extends Chain{

	
	ArrayList<Cliente> clientesEnLobby;
	
	public InvitarUsuario(ArrayList<Cliente> clientesEnLobby) {
		this.clientesEnLobby=clientesEnLobby;
	}
	
	@Override
	public void manejarPeticion(Mensaje mensaje) {
		String comando=mensaje.getComando();
		if (comando.equals(Comandos.InvitarUsuarioSalaPrivada)||comando.equals(Comandos.InvitarUsuarioSalaPublica)) {
			System.out.println("InvitarUsuario Recibio: "+mensaje.getComando());
			String[] valores = mensaje.getInformacion().split(";");
			String nombre = valores[0];

			for(Cliente c: clientesEnLobby) {
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
			System.out.println("Servidor: Ultimo eslabon. InvitarUSUARIO. El comando era: "+mensaje.getComando());
		}
	}

}
