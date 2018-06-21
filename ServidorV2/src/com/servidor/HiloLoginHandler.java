package com.servidor;

import java.io.IOException;

import java.net.Socket;

import com.DB.Comandos;
import com.DB.DAO_BaseDeDatos;
import com.cliente.ClientInputHandler;
import com.cliente.ClientOutputHandler;
import com.cliente.Cliente;
import com.logs.LoggerCliente;
import com.mensajes.Formato;
import com.mensajes.Mensaje;

/**
 * Clase para acceder a informacion en la base de datos. DAO = Data Access
 * Object. Se crea Uno por cada cliente que se conecta, se crea, hace lo que
 * hace y se destruye.
 * 
 * @author Maxi
 *
 */
public class HiloLoginHandler implements Runnable {
	boolean running;
	ClientOutputHandler salida;
	ClientInputHandler entrada;
	String userName;
	String password;
	ControladorServidor controlador;

	public HiloLoginHandler(Socket _socket) throws IOException {
		salida = new ClientOutputHandler(_socket);
		entrada = new ClientInputHandler(_socket);
		
		controlador = ControladorServidor.getInstance();
	}

	@Override
	public void run() {
		boolean usuarioValido = false;
		Cliente clienteNuevo=null;
		do {
			String usuarioYPassword[] = getDatosDeUsuario();
			String usuarioRecibido = usuarioYPassword[0];
			String passwordRecibido = usuarioYPassword[1];
			usuarioValido = validarUsuario(usuarioRecibido, passwordRecibido);
			
			contestarUsuario(usuarioValido);
			
			if(usuarioValido) {
				
				LoggerCliente.enviarLog("Usuario "+ usuarioRecibido +" ha entrado al chat.");
				clienteNuevo = new Cliente(usuarioRecibido, salida,entrada);
				try {
					controlador.meterEnLobby(clienteNuevo);
					clienteNuevo.iniciarEscucha();
					clienteNuevo.iniciarRespuesta();
					
				} catch (Exception e) {e.printStackTrace();}
			}
			


		} while (usuarioValido == false);
	}

	private String[] getDatosDeUsuario() {
		
		try {
			
			Mensaje mensajeRecibido = entrada.recibirMensaje();
			System.out.println("Cantidad Caracteres recibidos " + mensajeRecibido.toString().length());
			System.out.println(mensajeRecibido.toString());
			
			return mensajeRecibido.toString().split(" ");

		} catch (ClassNotFoundException | IOException e1) {
			LoggerCliente.enviarLog("No se pudo recibir los datos de usuario.");
			e1.printStackTrace();
		}

		return null;

	}

	private boolean validarUsuario(String usuario, String password) {
		System.out.println(usuario + " " + password);
		return DAO_BaseDeDatos.getInstance().validarUsuario(usuario, password);
	}

	public void contestarUsuario(boolean usuarioValido) { // REVISAR PARTE CLIENTE DEBE TENER IMPLEMENTADO SWITCH/CADENA DE RESPNB
			String respuesta=null;
			if (usuarioValido) {
				respuesta = "1";
				Mensaje contestacionLogin = new Mensaje(Comandos.LOGIN, respuesta);
				salida.enviarMensajeAlCliente(contestacionLogin);

			} else {
			    respuesta ="0";
				Mensaje contestacionLogin = new Mensaje(Comandos.LOGIN, respuesta);
				salida.enviarMensajeAlCliente(contestacionLogin);
			}


	}

}
