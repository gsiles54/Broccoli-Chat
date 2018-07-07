package com.cadena;

import static com.Cliente.Cliente.nombreCliente;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import com.Cliente.EntradaSalida;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.HiloOutputSala;
import com.salas.Sala;
import com.vista.GUI_Invitacion;
import com.vista.GUI_Lobby;
import com.vista.GUI_Sala;

public class ExisteSala extends ChainCliente{

	private ArrayList<Sala> salas;
	private GUI_Lobby lobbyGui;
	public ExisteSala(ArrayList<Sala> copiaSalasDisponibles, GUI_Lobby lobbyGui) {
		this.salas = copiaSalasDisponibles;
		this.lobbyGui = lobbyGui;
	}
	@Override
	public void manejarPeticion(Mensaje msj) {
		if(msj.getComando().equals(Comandos.ExisteSala)) {
			System.out.println("ExisteSala Recibio: " + msj.getComando());
		
			Integer idSala = Integer.valueOf(msj.getIDSala());
			
			for(Sala salaActual : salas) {
				if(salaActual.getSalaID().equals(idSala)) {
					mostrarGui(salaActual);
				}
			}
			
		}
		else {siguiente.manejarPeticion(msj);}
		
	}
	private void mostrarGui(Sala salaActual) {
		GUI_Sala guiSala = salaActual.getSalaGui();
		if(guiSala!=null) {
			guiSala.requestFocus();
		}else {
			DefaultListModel<String> modeloListaClientes = (DefaultListModel<String>) lobbyGui.getListaClientesConectados().getModel();
			guiSala = new GUI_Sala(modeloListaClientes);
			

			guiSala.setTitleSala(salaActual.getNombreSala());
			guiSala.setSala(salaActual);
			guiSala.setSalaID(salaActual.getSalaID());
			for(String clienteActual : salaActual.getClientesEnSala()){
				guiSala.agregarCliente(clienteActual);
			}
			
			HiloOutputSala hiloSala = new HiloOutputSala(guiSala,salaActual);
			Thread thSala = new Thread(hiloSala);
			thSala.start();
			thSala.setName("Output Sala: "+ salaActual.getNombreSala());
			salaActual.setHilo(hiloSala);
			salaActual.setSalaGui(guiSala);
			guiSala.setVisible(true);
	
			StringBuilder informacion = new StringBuilder();
			informacion.append(nombreCliente);
			informacion.append(';');
			informacion.append(salaActual.getNombreSala());
			informacion.append(';');
			informacion.append(salaActual.getSalaID());
			informacion.append(';');
			informacion.append(1);
			EntradaSalida.getInstance().escribirMensaje(new Mensaje(Comandos.ClienteDejandoConver,informacion.toString()));
			
		}
	}

}
