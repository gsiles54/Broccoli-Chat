package com.logs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

import javax.swing.JTextArea;
/**
 * Servidor de Logs, se queda a la escucha de mensajes de logs entrantes.
 * @author Maxi
 *
 */
public class LoggerServidor implements Runnable {
	JTextArea logOutput;
	ServerSocket serverSocket;
	int puerto = 2018;
	BufferedReader in;
	String lineaError; // el mensaje de error

	public LoggerServidor(JTextArea log) {
		logOutput = log;
		try {
			serverSocket = new ServerSocket(puerto);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {

				Socket socket = serverSocket.accept();
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				lineaError = in.readLine();
				escribirEnLog(lineaError);
				socket.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	//Esto realmente no necesita ser sincronizado. no ? hay un solo hilo accediendo al JTextArea :P
	public synchronized void escribirEnLog(String texto) {
		logOutput.append(Calendar.HOUR_OF_DAY+":"+Calendar.MINUTE+" "+texto + "\n");
	}
}
