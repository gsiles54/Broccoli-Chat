package com.utilitarios;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JLabel;
/*
 * Solo establece un enlace, una conexion, con el servidor. No se encarga de loguearse ni nada. Solo conectarse e informar
 * dicho estado de conexion: Online/Offline.
 */
public class HiloReconexion implements Runnable {
	Socket socket;
	JLabel label;
	
	public  HiloReconexion(Socket _socket, JLabel _label) {
		label=_label;
		socket=_socket;
	}

	@Override
	public void run() {
		try {
			socket = new Socket("localhost",1234); //PARAMETRIZAR ESTO			
			if(socket.isConnected()) {
				label.setText("");
				label.setText("Estado: Servidor Online");
			}
		} catch (IOException e) {
			label.setText("");
			label.setText("Estado: Servidor Offline");
		}

	}

}
