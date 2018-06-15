package com.vista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.Cliente.EntradaSalida;
import com.cadena.ChainCliente;
import com.cadena.CrearSala;
import com.cadena.MensajeASala;
import com.cadena.NuevoClienteConectado;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.Sala;

/**
 * PORqué tengo que meter esta clase en com.vista si es un controlador? solo para tener acceso directo a los componentes
 * GUI ??? otra forma ?
 * @author Maxi
 *
 */
public class ControladorCliente implements Runnable{
	//Solo se usa para mostrar clientes en el lobby o cuando quiero agregar gente a una conversacion.
	ArrayList<String> copiaClientesEnLobby; 
	DefaultListModel<String> modeloListaClientes;
	
	ArrayList<Conversacion> copiaConversacionesActivas;
	
	ArrayList<Sala> copiaSalasDisponibles;
	


	DefaultListModel<String> modeloListaSalas;
	
	EntradaSalida entradaSalida;
	
	GUI_Lobby lobbyGui;
	String cliente;



	static ControladorCliente cc=null;
	ChainCliente manejador=null;
	
	private ControladorCliente() {
	
		entradaSalida=EntradaSalida.getInstance();
		
		modeloListaClientes= new DefaultListModel<String>();
		modeloListaSalas= new DefaultListModel<String>();
		
		copiaClientesEnLobby= new ArrayList<> ();
		copiaSalasDisponibles = new ArrayList<>();
		
		lobbyGui=GUI_Lobby.guiLobby;
		lobbyGui.getListaClientesConectados().setModel(modeloListaClientes);
		
		
		//CLIENTE NUEVO ES PARA AVISAR AL CLIENTE NO AL SERVIDOR, DE QUE UN CLIENTE SE CONECTO.
		manejador = ensamblarChain();
	}
	
	public static synchronized ControladorCliente getInstance() {
		if(cc==null) {
			cc = new ControladorCliente();
			
		}
		return cc;
	}
	

	
	public void setCliente(String nombre) {
	   cliente = nombre; // LOBBY
		
		
	}
	public synchronized void manejarMensaje(Mensaje mensaje) {
		
		manejador.manejarPeticion(mensaje);
	}

	private ChainCliente ensamblarChain() {
		CrearSala cs = new CrearSala();
		MensajeASala msj = new MensajeASala();
		NuevoClienteConectado ncc= new NuevoClienteConectado(lobbyGui, modeloListaClientes,copiaClientesEnLobby);

		cs.enlazarSiguiente(msj);
		ncc.enlazarSiguiente(cs);
		return ncc;
	}
	
	@Override
	public void run() {
		
		while(true) {
				
				try {
					Mensaje mensajeRecibido = entradaSalida.recibirMensaje();
					System.out.println(mensajeRecibido.getComando()+" | "+mensajeRecibido.getInformacion());
					manejarMensaje(mensajeRecibido);
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}

		}
		
		
	}


	public void imprimirMsj(Mensaje mensaje) {
		for(Sala s: copiaSalasDisponibles) {
			if(mensaje.getIDSala().equals(-1)) {
				imprimirEnLobby(mensaje);
			}else {
				if(s.getSalaID().equals(mensaje.getIDSala()))
					imprimirEnSala(mensaje,s.getSalaGui());
			}
		}
	}
	
	private synchronized void imprimirEnLobby(Mensaje mensaje) {
		StyledDocument sd;
		
		if(!esParaEsteCliente(mensaje)) {// Hola Mundo
			sd = lobbyGui.getChatLobby().getStyledDocument();
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
			try {
				sd.insertString(sd.getLength(), mensaje.getInformacion(), null);
				sd.setParagraphAttributes(sd.getLength()+1, 1, center, false);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}else {
				SimpleAttributeSet attribute = new SimpleAttributeSet();
				StyleConstants.setAlignment(attribute, StyleConstants.ALIGN_RIGHT);
				
				sd=lobbyGui.getChatLobby().getStyledDocument();
				try {
					sd.insertString(sd.getLength(), mensaje.getInformacion(), null);
					sd.setParagraphAttributes(sd.getLength()+1, 1, attribute, false);
					
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
	
	}
	
	private synchronized void imprimirEnSala(Mensaje mensaje,GUI_Sala guiSala) {
		StyledDocument sd;
		
		if(!esParaEsteCliente(mensaje)) {// Hola Mundo
			sd = guiSala.getChatSala().getStyledDocument();
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
			try {
				sd.insertString(sd.getLength(), mensaje.getInformacion(), null);
				sd.setParagraphAttributes(sd.getLength()+1, 1, center, false);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}else {
				SimpleAttributeSet attribute = new SimpleAttributeSet();
				StyleConstants.setAlignment(attribute, StyleConstants.ALIGN_RIGHT);
				
				sd=guiSala.getChatSala().getStyledDocument();
				try {
					sd.insertString(sd.getLength(), mensaje.getInformacion(), null);
					sd.setParagraphAttributes(sd.getLength()+1, 1, attribute, false);
					
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
	
	}

	private boolean esParaEsteCliente(Mensaje mensaje) {
		String[] array = mensaje.getInformacion().split(" : ");
		
		// TODO Auto-generated method stub
		return array[0].equals('\n'+cliente);
	}

	public ArrayList<Sala> getCopiaSalasDisponibles() {
		return copiaSalasDisponibles;
	}
	public synchronized void agregarSala(Sala sala) {
		copiaSalasDisponibles.add(sala);
	}
	public synchronized void quitarSala(Sala sala) {
		
		copiaSalasDisponibles.remove(sala);
		
	}
	public synchronized String getCliente() {
		return cliente;
	}
	
	public EntradaSalida getEntradaSalida() {
		return entradaSalida;
	}
}
