package com.Chain;


import java.util.ArrayList;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.sala.Sala;

public class EliminarConversacion extends Chain{
	
	private ArrayList<Sala> salas;
	public EliminarConversacion(ArrayList<Sala> salas) {
	this.salas = salas;
	}
	@Override
	public void manejarPeticion(Mensaje mensaje) {
		if (mensaje.getComando().equals(Comandos.ClienteDejandoConver)) {
			System.out.println("Cliente dejando conver recibio: "+mensaje.getComando());
			String[] valores = mensaje.getInformacion().split(";");
			Integer idSala = Integer.valueOf(valores[2]);
			Integer valor = Integer.valueOf(valores[3]);
			for(int i = 0 ; i <salas.size() ; i++){
				Sala salaActual = salas.get(i);
					if(salaActual.getSalaID().equals(idSala)){
					salaActual.setCantUsuariosEnConver(valor);
					if(salaActual.getCantUsuariosEnConver()<=0)
						salas.remove(i);
				}
			}

			
		} else {
			siguiente.manejarPeticion(mensaje);
		}
	}

}
