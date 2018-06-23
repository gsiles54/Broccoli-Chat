package com.cadena;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;

public class ClienteSaliendo extends ChainCliente {

	public ClienteSaliendo() {
		
	}
	
	
	@Override
	public void manejarPeticion(Mensaje mensaje) {
		
			if(mensaje.getComando().equals(Comandos.ClienteSaliendo)) {
				System.out.println("ClienteSaliendo recibio: "+ mensaje.getComando());
				
				
			}else {System.out.println("ClienteSaliendo recibio: "+mensaje.getComando()+"\nAgregar mas comandos");}
	}

}
