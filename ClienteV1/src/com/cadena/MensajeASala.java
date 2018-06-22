package com.cadena;

import java.util.ArrayList;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.Sala;
import com.vista.ControladorCliente;

public class MensajeASala extends ChainCliente{
	
	ArrayList<Sala>copiaSalasDisponibles;
	ControladorCliente controladorCliente;
	
	public MensajeASala(ArrayList<Sala> copiaSalasDisponibles, ControladorCliente contCliente) {
		this.copiaSalasDisponibles=copiaSalasDisponibles;
		controladorCliente=contCliente;
	}

	@Override
	public void manejarPeticion(Mensaje msj) {
		if(msj.getComando().equals(Comandos.MensajeASala)) {
			imprimirMsj(msj);
		}
		else {siguiente.manejarPeticion(msj);}
	}

	private void imprimirMsj(Mensaje mensaje) {
		for(Sala s: copiaSalasDisponibles) {
			if(mensaje.getIDSala().equals(-1)&&s.getSalaID().equals(mensaje.getIDSala())) {
				controladorCliente.imprimirEnLobby(mensaje);
			}else {
				if(s.getSalaID().equals(mensaje.getIDSala()))
					controladorCliente.imprimirEnSala(mensaje,s.getSalaGui());
			}
		}
	}
	
	
}
