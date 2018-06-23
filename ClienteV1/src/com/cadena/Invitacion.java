package com.cadena;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.vista.GUI_Invitacion;

public class Invitacion extends ChainCliente{

	@Override
	public void manejarPeticion(Mensaje msj) {

		if(msj.getComando().equals(Comandos.InvitacionASalaPrivada)||msj.getComando().equals(Comandos.InvitacionASalaPublica)) {
			System.out.println("Invitacion Recibio: " + msj.getComando());
			String[] valores = msj.getInformacion().split(";");
			
			String nombreSala = valores[1];
			String idSala = valores[2];
			String nombreUsuario = valores[3];
			
			boolean esPrivada = msj.getComando().equals(Comandos.InvitacionASalaPrivada)?true:false;
			new GUI_Invitacion(nombreUsuario,nombreSala,idSala,esPrivada);
		}
		else {siguiente.manejarPeticion(msj);}
	}

}
