package com.vista;

import static com.Cliente.Cliente.nombreCliente;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.Cliente.EntradaSalida;
import com.cadena.AgregarASala;
import com.cadena.ChainCliente;
import com.cadena.ClienteSaliendo;
import com.cadena.CrearSala;
import com.cadena.Invitacion;
import com.cadena.MensajeASala;
import com.cadena.NuevoClienteConectado;
import com.mensajes.Mensaje;
import com.salas.Sala;

/**
 * PORqué tengo que meter esta clase en com.vista si es un controlador? solo
 * para tener acceso directo a los componentes GUI ??? otra forma ?
 * 
 * @author Maxi
 *
 */
public class ControladorCliente implements Runnable {
	
	private final boolean corriendo=true;
	
	// Solo se usa para mostrar clientes en el lobby o cuando quiero agregar gente a
	// una conversacion.
	ArrayList<String> copiaClientesEnLobby;
	DefaultListModel<String> modeloListaClientes;

	ArrayList<Sala> copiaSalasDisponibles;

	DefaultListModel<String> modeloListaSalas;

	EntradaSalida entradaSalida;

	GUI_Lobby lobbyGui;

	ChainCliente manejador = null;

	public ControladorCliente(GUI_Lobby lobbyGui) {

		entradaSalida = EntradaSalida.getInstance();

		modeloListaClientes = new DefaultListModel<String>();
		modeloListaSalas = new DefaultListModel<String>();

		copiaClientesEnLobby = new ArrayList<>();
		copiaSalasDisponibles = new ArrayList<>();

		this.lobbyGui = lobbyGui;
		lobbyGui.getListaClientesConectados().setModel(modeloListaClientes);

		manejador = ensamblarChain();
	}

	public synchronized void manejarMensaje(Mensaje mensaje) {

		manejador.manejarPeticion(mensaje);
	}

	private ChainCliente ensamblarChain() {
		CrearSala crearSala = new CrearSala(copiaSalasDisponibles, modeloListaClientes);
		MensajeASala mensajeASala = new MensajeASala(copiaSalasDisponibles, this);
		NuevoClienteConectado nuevoClienteConectado = new NuevoClienteConectado(lobbyGui, modeloListaClientes,copiaClientesEnLobby);
		Invitacion invitacion = new Invitacion();
		AgregarASala agregarASala = new AgregarASala(copiaSalasDisponibles, modeloListaClientes);
		ClienteSaliendo clienteSaliendo= new ClienteSaliendo();

		crearSala.enlazarSiguiente(mensajeASala);
		mensajeASala.enlazarSiguiente(nuevoClienteConectado);
		nuevoClienteConectado.enlazarSiguiente(invitacion);
		invitacion.enlazarSiguiente(agregarASala);
		agregarASala.enlazarSiguiente(clienteSaliendo);

		return crearSala;
	}

	@Override
	public void run() {
		try {
			while (corriendo) {
				if (entradaSalida != null && entradaSalida.entradaSalidaAbierta()) {
					Mensaje mensajeRecibido = entradaSalida.recibirMensaje();
					manejarMensaje(mensajeRecibido);
				}
			}
		} catch (Exception s) {}

	}

	// no borrar
	public synchronized void imprimirEnLobby(Mensaje mensaje) {
		StyledDocument styledDocument;

		if (!esParaEsteCliente(mensaje)) {
			styledDocument = lobbyGui.getChatLobby().getStyledDocument();
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
			try {
				styledDocument.insertString(styledDocument.getLength(), mensaje.getInformacion(), null);
				styledDocument.setParagraphAttributes(styledDocument.getLength() + 1, 1, center, false);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}

		} else {
			SimpleAttributeSet attribute = new SimpleAttributeSet();
			StyleConstants.setAlignment(attribute, StyleConstants.ALIGN_RIGHT);

			styledDocument = lobbyGui.getChatLobby().getStyledDocument();
			try {
				styledDocument.insertString(styledDocument.getLength(), mensaje.getInformacion(), null);
				styledDocument.setParagraphAttributes(styledDocument.getLength() + 1, 1, attribute, false);

			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}

	}

	// no borrar
	public synchronized void imprimirEnSala(Mensaje mensaje, GUI_Sala guiSala) {
		StyledDocument sd;

		if (!esParaEsteCliente(mensaje)) {// Hola Mundo
			sd = guiSala.getChatSala().getStyledDocument();
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
			try {
				sd.insertString(sd.getLength(), mensaje.getInformacion(), null);
				sd.setParagraphAttributes(sd.getLength() + 1, 1, center, false);
			} catch (BadLocationException e) {

				e.printStackTrace();
			}

		} else {
			SimpleAttributeSet attribute = new SimpleAttributeSet();
			StyleConstants.setAlignment(attribute, StyleConstants.ALIGN_RIGHT);

			sd = guiSala.getChatSala().getStyledDocument();
			try {
				sd.insertString(sd.getLength(), mensaje.getInformacion(), null);
				sd.setParagraphAttributes(sd.getLength() + 1, 1, attribute, false);

			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}

	}
	


	private boolean esParaEsteCliente(Mensaje mensaje) {
		String[] array = mensaje.getInformacion().split(" : ");
		return array[0].equals('\n' + nombreCliente);
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

	public EntradaSalida getEntradaSalida() {
		return entradaSalida;
	}
}
