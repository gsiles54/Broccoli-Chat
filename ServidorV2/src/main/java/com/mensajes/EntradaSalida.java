package com.mensajes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class EntradaSalida {
	Socket socket;
	ObjectInputStream objectIn;
	ObjectOutputStream objectOut;
	
	
	public EntradaSalida(Socket socket) {
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
	
	public Mensaje recibirMensaje() throws ClassNotFoundException, IOException {
		return (Mensaje) objectIn.readObject();
	}

	
}
