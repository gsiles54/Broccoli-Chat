package com.cliente;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.mensajes.Mensaje;
import com.servidor.ControladorServidor;

/**
 * Utiliza ObjectInputStream, NO usa DataInputStream.
 * Se encarga de como responder ante las entradas de un cliente. Existe uno para cada Cliente.
 * Esta clase recibe un objeto del tipo IMensaje.
 * @author Maxi
 *
 */
public class ClientInputHandler implements Runnable {

	
	Mensaje mensaje;
	boolean conectado=true;
	ObjectInputStream objectIn;
	Socket socket;
	Cliente cliente; //Si un Cliente sabe del socket, y el Socket sabe del Cliente, algo raro hay. shhhhhhhhhhhhhhhhhhhhhhh
	
	public ClientInputHandler(Socket socket) throws IOException {
	
		this.socket=socket;
		objectIn = new ObjectInputStream(socket.getInputStream());
	}
	
	@Override
	public void run() {
		
		ControladorServidor cs = ControladorServidor.getInstance();
		while (conectado) {

				try {
					mensaje= recibirMensaje();
					if(mensaje!=null) {
						System.out.println(mensaje.getComando()+" "+mensaje.getInformacion());
						cs.manejarMensaje(mensaje);
					}
					
				} catch (ClassNotFoundException | IOException e) {System.out.println("CIH cerrado"); conectado=false;}

		}
	}
	
	public Mensaje recibirMensaje() throws ClassNotFoundException, IOException {
		return (Mensaje) objectIn.readObject();
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente=cliente;
	}
}
