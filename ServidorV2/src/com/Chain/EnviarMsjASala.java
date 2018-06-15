package com.Chain;

import java.util.ArrayList;

import com.cliente.Cliente;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.sala.Sala;
import com.servidor.ControladorServidor;

/*Si el mensaje es un simple texto dirigido a la sala, llama al metodo que 
 * manda el mensaje a la sala que corresponda*/
public class EnviarMsjASala extends Chain{

	
	
	public EnviarMsjASala(ArrayList<Cliente> _clientesEnLobby,ArrayList<Sala> _salas) {
		clientesEnLobby=_clientesEnLobby;
		 salas=_salas;
	}
	
	@Override
	public void manejarPeticion(Mensaje mensaje) {
		
		           
		if (mensaje.getComando().equals(Comandos.MensajeASala)) {
			cs=ControladorServidor.getInstance();
				salas=cs.getSalas();
				for (Sala s : salas) {
						if (s.getSalaID().equals(mensaje.getSalaID()))
							s.enviarMensaje(mensaje);
					}
		}
		else
		{	
			System.out.println("Ultimo eslabon. El comando era: "+mensaje.getComando());
			System.out.println("Agregar mas manejadores");
		}
	}
}
