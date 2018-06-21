package com.Chain;

import com.mensajes.Mensaje;

/* Cadena de responsabilidad para manejar los mjs*/
public abstract class Chain {
		
		protected Chain siguiente;

		public void enlazarSiguiente(Chain sig) {
			siguiente=sig;
		}
		
				
		public abstract void manejarPeticion(Mensaje p);
}
