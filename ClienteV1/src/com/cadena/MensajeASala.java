package com.cadena;

import static com.Cliente.Cliente.nombreCliente;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import com.Cliente.EntradaSalida;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.HiloOutputSala;
import com.salas.Sala;
import com.vista.ControladorCliente;
import com.vista.GUI_Lobby;
import com.vista.GUI_Sala;

public class MensajeASala extends ChainCliente{
	
	ArrayList<Sala>copiaSalasDisponibles;
	ControladorCliente controladorCliente;
	private GUI_Lobby lobbyGui;
	
	public MensajeASala(ArrayList<Sala> copiaSalasDisponibles, ControladorCliente contCliente, GUI_Lobby lobbyGui) {
		this.copiaSalasDisponibles=copiaSalasDisponibles;
		controladorCliente=contCliente;
		this.lobbyGui = lobbyGui;
	}

	@Override
	public void manejarPeticion(Mensaje msj) {
		
		if(msj.getComando().equals(Comandos.MensajeASala)) {
			System.out.println("MensajeASala Recibio: " + msj.getComando());
			imprimirMsj(msj);
		}
		else {siguiente.manejarPeticion(msj);}
	}

	private void imprimirMsj(Mensaje mensaje) {
		for(Sala s: copiaSalasDisponibles) {
			if(mensaje.getIDSala().equals(-1)&&s.getSalaID().equals(mensaje.getIDSala())) {
				
				controladorCliente.imprimirEnLobby(mensaje);
			}else {
				if(s.getSalaID().equals(mensaje.getIDSala())){
					if(s.getSalaGui()==null&&s.isConversacion()){
						crearGUISala(s);
						StringBuilder informacion = new StringBuilder();
						informacion.append(nombreCliente);
						informacion.append(';');
						informacion.append(s.getNombreSala());
						informacion.append(';');
						informacion.append(s.getSalaID());
						informacion.append(';');
						informacion.append(1);
						EntradaSalida.getInstance().escribirMensaje(new Mensaje(Comandos.ClienteDejandoConver,informacion.toString()));
					}
					
					controladorCliente.imprimirEnSala(mensaje,s.getSalaGui());
				}
					
			}
		}
	}
	private void crearGUISala(Sala salaAModificar){
		System.out.println("se creo el gui");
		DefaultListModel<String> clientesConectados = (DefaultListModel<String>) lobbyGui.getListaClientesConectados().getModel();
		GUI_Sala guiSala = new GUI_Sala(clientesConectados);
		guiSala.setTitleSala(salaAModificar.getNombreSala());
		guiSala.setSalaID(salaAModificar.getSalaID());
		guiSala.setVisible(true); 
		for(String clienteActual : salaAModificar.getClientesEnSala()){
			guiSala.agregarCliente(clienteActual);
		}
		
	
		guiSala.setSala(salaAModificar);
		
		HiloOutputSala hiloSala = new HiloOutputSala(guiSala, salaAModificar);
		Thread thSala = new Thread(hiloSala, "Hilo-Sala");
		thSala.setName("Output Sala: " + salaAModificar.getNombreSala());
		salaAModificar.setHilo(hiloSala);
		salaAModificar.setSalaGui(guiSala);
		thSala.start();

	}
	
}
