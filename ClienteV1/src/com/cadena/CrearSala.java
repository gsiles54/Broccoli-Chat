package com.cadena;

import static com.Cliente.Cliente.nombreCliente;

import java.util.ArrayList;
import javax.swing.DefaultListModel;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.HiloOutputSala;
import com.salas.Sala;
import com.vista.GUI_Lobby;
import com.vista.GUI_Sala;

public class CrearSala extends ChainCliente {

	ArrayList<Sala> copiaSalasDisponibles;

	private GUI_Lobby lobbyGUI;

	public CrearSala(ArrayList<Sala> copiaSalasDisponibles, GUI_Lobby lobbyGui) {
		this.copiaSalasDisponibles = copiaSalasDisponibles;
		this.lobbyGUI = lobbyGui;
	}

	@Override
	public void manejarPeticion(Mensaje mensaje) {

		if (mensaje.getComando().equals(Comandos.SalaPrivCreadaExitosamente)
				|| mensaje.getComando().equals(Comandos.SalaPubCreadaExitosamente)) {
			System.out.println("CrearSala Recibio: " + mensaje.getComando());

			String valores[] = mensaje.getInformacion().split(";");
			String nombreSala = valores[0];
			Integer idSala = Integer.valueOf(valores[1]);
			String clienteEmisor = valores[2];
			boolean esPrivada = mensaje.getComando().equals(Comandos.SalaPrivCreadaExitosamente) ? true : false;
			Sala nuevaSala;
			if (clienteEmisor.equals(nombreCliente)) {
				DefaultListModel<String> clientesConectados = (DefaultListModel<String>) lobbyGUI.getListaClientesConectados().getModel();
				GUI_Sala guiSala = new GUI_Sala(clientesConectados);

				guiSala.setTitleSala(nombreSala);
				guiSala.setSalaID(idSala);
				guiSala.setVisible(true); 
				guiSala.agregarCliente(nombreCliente);
				if(valores.length>3)
					guiSala.agregarCliente(valores[3]);
				nuevaSala = new Sala(idSala, nombreSala, esPrivada, guiSala);

				guiSala.setSala(nuevaSala);
				
				HiloOutputSala hiloSala = new HiloOutputSala(guiSala, nuevaSala);
				Thread thSala = new Thread(hiloSala, "Hilo-Sala");
				thSala.setName("Output Sala: " + nombreSala);
				nuevaSala.setHilo(hiloSala);
				thSala.start();

			} else {	
				nuevaSala = new Sala(idSala,nombreSala,esPrivada);
			
			}	
			nuevaSala.meterCliente(clienteEmisor);
			copiaSalasDisponibles.add(nuevaSala);
			if(!esPrivada){
			lobbyGUI.agregarSala(nombreSala);
			}
			
			if(valores.length>3){
				nuevaSala.meterCliente(valores[3]);
				nuevaSala.setConversacion(true);
			
			}
		} else {
			siguiente.manejarPeticion(mensaje);
		}
	}

}
