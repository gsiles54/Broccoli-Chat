package com.servidor;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.DB.DAO_BaseDeDatos;
import com.cliente.ClientInputHandler;
import com.cliente.ClientOutputHandler;
import com.cliente.Cliente;
import com.logs.LoggerCliente;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.sala.Sala;

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
		Cliente clienteNuevo = null;
		do {
			String usuarioYPassword[] = getDatosDeUsuario();
			String usuarioRecibido = usuarioYPassword[0];
			String passwordRecibido = usuarioYPassword[1];
			usuarioValido = validarUsuario(usuarioRecibido, passwordRecibido);

			contestarUsuario(usuarioValido);

			if (usuarioValido) {

				LoggerCliente.enviarLog("Usuario " + usuarioRecibido + " ha entrado al chat.");
				clienteNuevo = new Cliente(usuarioRecibido, salida, entrada);
				try {
					controlador.meterEnLobby(clienteNuevo);
					clienteNuevo.iniciarEscucha();
					clienteNuevo.iniciarRespuesta();
					List<Sala> salasDisp = controlador.getSalas();
					if (!salasDisp.isEmpty()) {
						StringBuilder informacion = new StringBuilder();
						for (Sala salaActual : salasDisp) {
							if (!salaActual.getSalaID().equals(-1)) {
								informacion.append(salaActual.getSalaID());
								informacion.append(";");
								informacion.append(salaActual.getNombre());
								informacion.append(";");
								informacion.append(salaActual.esPrivada() ? "esPriv" : "noEsPriv");
								informacion.append(";");
								for (int i = 0, sizeClientes = salaActual.getClientesEnSala()
										.size(); i < sizeClientes; i++) {
									Cliente clienteActual = salaActual.getClientesEnSala().get(i);
									informacion.append(clienteActual.getNombre());
									informacion.append(";");
								}
								informacion.append("$");
							}

						}
						if(informacion.length()>0){
									informacion.deleteCharAt(informacion.length() - 1);

									clienteNuevo.enviarMensaje(new Mensaje(Comandos.AgregarSalaGUI, informacion.toString()));
				
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} while (usuarioValido == false);
	}

	private String[] getDatosDeUsuario() {

		try {

			Mensaje mensajeRecibido = entrada.recibirMensaje();

			return mensajeRecibido.toString().split(" ");

		} catch (ClassNotFoundException | IOException e1) {
			LoggerCliente.enviarLog("No se pudo recibir los datos de usuario.");
			e1.printStackTrace();
		}

		return null;

	}

	private boolean validarUsuario(String usuario, String password) {
		return DAO_BaseDeDatos.getInstance().validarUsuario(usuario, password);
	}

	public void contestarUsuario(boolean usuarioValido) { // REVISAR PARTE
															// CLIENTE DEBE
															// TENER
															// IMPLEMENTADO
															// SWITCH/CADENA DE
															// RESPNB
		String respuesta = null;
		if (usuarioValido) {
			respuesta = "1";
			Mensaje contestacionLogin = new Mensaje(Comandos.LOGIN, respuesta);
			salida.enviarMensajeAlCliente(contestacionLogin);

		} else {
			respuesta = "0";
			Mensaje contestacionLogin = new Mensaje(Comandos.LOGIN, respuesta);
			salida.enviarMensajeAlCliente(contestacionLogin);
		}

	}

}
