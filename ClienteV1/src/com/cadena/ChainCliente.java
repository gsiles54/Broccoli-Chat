package com.cadena;

import com.mensajes.Mensaje;


public abstract class ChainCliente {
	protected ChainCliente siguiente;

	public void enlazarSiguiente(ChainCliente sig) {
		siguiente=sig;
	}
	
	public abstract void manejarPeticion(Mensaje mensaje);
			
}
