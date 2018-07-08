package com.Chain;

import java.util.ArrayList;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.sala.Sala;


/*Si el mensaje es un simple texto dirigido a la sala, llama al metodo que 
 * manda el mensaje a la sala que corresponda*/
public class EnviarMsjASala extends Chain {

	private ArrayList<Sala> salas;

	public EnviarMsjASala(ArrayList<Sala> _salas) {
		salas = _salas;
	}

	@Override
	public void manejarPeticion(Mensaje mensaje) {
		if (mensaje.getComando().equals(Comandos.MensajeASala)) {
			System.out.println("EnviarMsjASala Recibio: "+mensaje.getComando());
			for (Sala s : salas) {
				if (s.getSalaID().equals(mensaje.getSalaID()))
					s.enviarMensaje(mensaje);
					
				
					
			}
		} else {
			siguiente.manejarPeticion(mensaje);
		}
	}
}
