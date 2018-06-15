package com.Chain;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.sala.Sala;
import com.servidor.ControladorServidor;

public class ClienteNuevo extends Chain{

	public ClienteNuevo() {
		
	}
	@Override
	public void manejarPeticion(Mensaje mensaje) {
		if (mensaje.getComando().equals(Comandos.ClienteNuevo)) {
			cs=ControladorServidor.getInstance();
			salas=cs.getSalas();
				for (Sala s : salas) {
						if (s.getSalaID().equals(-1))
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
