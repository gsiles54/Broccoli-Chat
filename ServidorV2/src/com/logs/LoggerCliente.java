package com.logs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
/**
 * La conexion solo dura lo suficiente para que envie el mensaje. Luego se pierde. Solo se encarga de enviar mensajes
 * Al servidor de logs. El puerto no sera recibido por parametro ya que es una clase de uso interno.
 * @author Maxi
 *
 */
public class LoggerCliente {
	private static Socket socket;
	private static  int puerto = 2018;
	private static  BufferedWriter out;

	public static void enviarLog(String mensajeError) {
		try {
			socket = new Socket("localhost", puerto);
			out= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			out.write(mensajeError);
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
