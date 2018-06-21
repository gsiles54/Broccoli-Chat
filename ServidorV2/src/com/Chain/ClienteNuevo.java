package com.Chain;

import java.util.ArrayList;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.sala.Sala;

public class ClienteNuevo extends Chain{
	
	private ArrayList<Sala> salas;
	
	public ClienteNuevo(ArrayList<Sala> salas) {
		this.salas=salas;
	}
	
	@Override
	public void manejarPeticion(Mensaje mensaje) {
		
		if (mensaje.getComando().equals(Comandos.ClienteNuevo)) {
				for (Sala s : salas) {
						if (s.getSalaID().equals(-1))
							s.enviarMensaje(mensaje);
					}
		}
		else{siguiente.manejarPeticion(mensaje);}
		
	}

}
