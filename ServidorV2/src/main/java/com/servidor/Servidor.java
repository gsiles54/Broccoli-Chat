package com.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

/**
 * Se encarga de recibir nuevos clientes y atenderlos. Al inicio los clientes se conectan automaticamnete, y son
 * agregados a una lista en el lobby del servidor. Alli el servidor aguarda por su usuario y contraseña.
 * Si eso se valida, se mueve el usuario al despachador donde finalmente recibira y enviara mensajes.
 * 
 * @author Maxi
 *
 */
public class Servidor implements Runnable {
	int puerto;
	boolean corriendo = true;
	ServerSocket serverSocket;
	JTextArea jTextAreaLogs;

	public Servidor(int _puerto, JTextArea _jTextAreaLogs)  {
		puerto = _puerto;
		jTextAreaLogs = _jTextAreaLogs;
		try {
			serverSocket = new ServerSocket(puerto);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {// Crea el socket y lo envia al hilo loginHandler.
		Socket nuevoSocket = null;
		while (corriendo) {

			try {
				nuevoSocket = serverSocket.accept();
				Thread tNuevoLogin = new Thread(new HiloLoginHandler(nuevoSocket));
				tNuevoLogin.start();
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
				corriendo=false; //para que deje de correr y salga del hilo,
			}
		}	
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
