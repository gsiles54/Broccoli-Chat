package com.cliente;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.mensajes.Mensaje;

public class ClientOutputHandler implements Runnable{

	ObjectOutputStream objectOut;
	Socket socket;
	List<Mensaje> lMensaje = new ArrayList<Mensaje>();
	Cliente cliente;
	boolean flag;
	
	public ClientOutputHandler(Socket socket) throws IOException {
		this.socket=socket;
		objectOut=new ObjectOutputStream(socket.getOutputStream());
	}
	
	private synchronized Mensaje siguienteMensaje() throws InterruptedException {
		while(lMensaje.isEmpty())
			wait();
		
		Mensaje mensaje = lMensaje.get(0);
		lMensaje.remove(0);
		return mensaje;
	}
	
	public synchronized void enviarMensaje(Mensaje mensaje) {
		lMensaje.add(mensaje);
		notify();
	}
	
	public synchronized void enviarMensajeAlCliente(Mensaje mensaje) {
		try {
			if(socket.isConnected()){
				objectOut.writeObject(mensaje);
			}else{
				// se cayo la conexion con el cliente. hacer algo(?)
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		flag=true;
		
		while(flag) {
			try {
				Mensaje msj = siguienteMensaje();
				enviarMensajeAlCliente(msj);
				System.out.println("Se envio el msj " + msj);
				
			} catch (InterruptedException e) {

				e.printStackTrace();
				
			}
		}
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente=cliente;
	}
	
	public void cerrarConexion() {
		flag=false;
		try {
			objectOut.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("Socket cerrado , ver cerrar tambien OIS OOS");
			e.printStackTrace();
		}
	}
}
