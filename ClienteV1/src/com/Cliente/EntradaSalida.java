package com.Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.mensajes.Mensaje;

public class EntradaSalida {
	Socket socket;
	ObjectInputStream objectIn;
	ObjectOutputStream objectOut;
	static EntradaSalida entradasalida;
	
	private EntradaSalida() {
		entradasalida=this;
	}
	
	public static synchronized EntradaSalida getInstance() {
		if(entradasalida==null) {
			new EntradaSalida();
		}
		return entradasalida;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
		try {
			objectOut = new ObjectOutputStream(socket.getOutputStream());
			objectIn = new ObjectInputStream(socket.getInputStream());
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void escribirMensaje(Mensaje mensaje) {
		try {
			objectOut.writeObject(mensaje);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Mensaje recibirMensaje() {
		Mensaje devuelve=null;
		try {
			if(objectIn!=null)
			devuelve= (Mensaje) objectIn.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return devuelve;
	}
	public boolean entradaSalidaAbierta() {
		if (objectIn!=null && objectOut!=null)
			return true;
		else
			return false;
	}
	
	public void cerrarEntradaSalida() {
		try {
			objectIn.close();
			objectOut.close();
			objectIn=null;
			objectOut=null;
	} catch (IOException e) {
		e.printStackTrace();
	} 
		}

	
}
