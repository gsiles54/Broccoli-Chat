package com.cadena;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.vista.ControladorCliente;

public class MensajeASala extends ChainCliente{

	@Override
	public void manejarPeticion(Mensaje msj) {
		if(msj.getComando().equals(Comandos.MensajeASala)) {
			cl = ControladorCliente.getInstance();
			cl.imprimirMsj(msj);
		}
		else {
			System.out.println("MensajeASala: agregar manejador siguiente");
		}
	}

	
	
}
