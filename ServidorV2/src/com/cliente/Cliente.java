package com.cliente;

import java.io.IOException;
import java.net.Socket;


import com.mensajes.Mensaje;

/**
 * Con esto se maneja el chat, sabe del socket y del nombre.
 * @author Maxi
 *
 */
public class Cliente {

	String nombre;
	ClientOutputHandler salida;
	ClientInputHandler entrada;
	
	public Cliente(String _nombre, Socket socket) throws IOException {
		nombre = _nombre; 
		
		entrada= new ClientInputHandler(socket);
		salida = new ClientOutputHandler(socket);
	}
	
	public Cliente(String _nombre, ClientOutputHandler salida, ClientInputHandler entrada) {
		this.entrada=entrada;
		this.nombre=_nombre;
		this.salida=salida;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void iniciarEscucha() {
		Thread t1 = new Thread(entrada);
		t1.start();
	}
	
	public void iniciarRespuesta() {
		Thread t1 = new Thread(salida);
		t1.start();
	}
	
	public void enviarMensaje(Mensaje mensaje) {
		salida.enviarMensaje(mensaje);
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	public ClientOutputHandler getSalida() {
		return salida;
	}

	public void setSalida(ClientOutputHandler salida) {
		this.salida = salida;
	}

	public ClientInputHandler getEntrada() {
		return entrada;
	}

	public void setEntrada(ClientInputHandler entrada) {
		this.entrada = entrada;
	}
	
	public void cerrarSockets() {
		salida.cerrarConexion();
	}
	
	
}
