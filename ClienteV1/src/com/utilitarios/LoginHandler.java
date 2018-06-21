package com.utilitarios;


import java.io.IOException;

import java.net.Socket;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import com.Cliente.Cliente;
import com.Cliente.EntradaSalida;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.HiloOutputLobby;
import com.salas.Sala;
import com.vista.ControladorCliente;
import com.vista.GUI_Lobby;
import com.vista.GUI_Login;
/*
 * Esta clase solo se dedica a loguearse e informar si salio bien o mal. Si sale bien, pasa el socket a otro lado.
 * Ver de enviar esta info por canal encriptado ?
 */


public class LoginHandler implements Runnable {

	boolean running;
	EntradaSalida entradaSalida;


	String userName;
	String password;
	GUI_Login loginGui;
	private ControladorCliente controladorCliente;
	private Thread hiloControladorCliente;
	private Thread hiloOutput;
	private Cliente cliente;
	private HiloOutputLobby output;
	//ESTE SOCKET NO LO TENGO Q PERDER!
	public  LoginHandler(Socket _socket, GUI_Login _loginGui) {
		
			entradaSalida=EntradaSalida.getInstance();
			entradaSalida.setSocket(_socket);
			loginGui=_loginGui;
	}
	
	public void enviarUserPass() {
		
		String userPass= loginGui.getUsername()+" "+loginGui.getPassword();
		Mensaje credenciales= new Mensaje(Comandos.LOGIN,userPass);
		entradaSalida.escribirMensaje(credenciales);
	}

	@Override
	public void run() {
		boolean flag=true;
		StyledDocument sd;
			while(flag) {
				//System.out.println(""); //VAYA A SABER UNO, SI SACO ESTO NO ANDA
				if(loginGui.isBoton()) {
					loginGui.setBoton(false);
					enviarUserPass();
						
						Mensaje resultadoLogin=entradaSalida.recibirMensaje();
						
						int fueExitoso= Integer.parseInt(new String(resultadoLogin.getInformacion()));
						if(fueExitoso!=0) {
							ocultarVentanaLogin();
							
							//******
							
							userName=loginGui.getUsername();
							GUI_Lobby lobbyGui= new GUI_Lobby(userName);
							controladorCliente= ControladorCliente.getInstance();
						
							Sala lobby= new Sala(-1,"Lobby",false);
							lobby.meterCliente(userName);
							controladorCliente.setCliente(userName);
							controladorCliente.getCopiaSalasDisponibles().add(lobby); 
							
							controladorCliente.getEntradaSalida().escribirMensaje(new Mensaje(Comandos.ClienteNuevo, userName));

							hiloControladorCliente = new Thread(controladorCliente);
							hiloControladorCliente.start();
							cliente = new Cliente(userName);
							output= new HiloOutputLobby(cliente,lobbyGui);
							hiloOutput = new Thread(output);
							hiloOutput.start();
							lobbyGui.setVisible(true);
							sd=lobbyGui.getChatLobby().getStyledDocument();
							
							try {
								sd.insertString(sd.getLength(), "Bienvenido a la sala.", null);
							} catch (BadLocationException e) {
								e.printStackTrace();
							}
							flag=false;
						}

				
					}
				}
			
	}

	private void ocultarVentanaLogin() {
		loginGui.setVisible(false);
	}
	public EntradaSalida getEntradaSalida() {
		return entradaSalida;
	}

}
