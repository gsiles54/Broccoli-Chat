package com.cadena;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.vista.ControladorCliente;
import com.vista.GUI_Invitacion;

public class Invitacion extends ChainCliente{

	@Override
	public void manejarPeticion(Mensaje msj) {
		// TODO Auto-generated method stub
		if(msj.getComando().equals(Comandos.InvitacionASalaPrivada)||msj.getComando().equals(Comandos.InvitacionASalaPublica)) {
			cl = ControladorCliente.getInstance();
			String[] valores = msj.getInformacion().split(";");
			String nombreUsuario = valores[0];
			String nombreSala = valores[1];
			String idSala = valores[2];
			boolean esPrivada = msj.getComando().equals(Comandos.InvitacionASalaPrivada)?true:false;
			new GUI_Invitacion(nombreUsuario,nombreSala,idSala,esPrivada);
		}
		else {
			siguiente.manejarPeticion(msj);
		}
	}

}
