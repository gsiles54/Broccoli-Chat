package com.Chain;

import java.util.ArrayList;

import com.cliente.Cliente;
import com.mensajes.Mensaje;
import com.sala.Sala;
import com.servidor.ControladorServidor;

/* Cadena de responsabilidad para manejar los mjs*/
public abstract class Chain {
		
		ArrayList<Cliente> clientesEnLobby;
		ArrayList<Sala> salas;
		ControladorServidor cs;
		protected Chain siguiente;

		public void enlazarSiguiente(Chain sig) {
			siguiente=sig;
		}
		
				
		public abstract void manejarPeticion(Mensaje p);
}
