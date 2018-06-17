package com.Chain;

import com.cliente.Cliente;
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
							//aTodos_ClienteConectado(mensaje);
					}
		}
		else
		{	
			siguiente.manejarPeticion(mensaje);
		}
	}

}
