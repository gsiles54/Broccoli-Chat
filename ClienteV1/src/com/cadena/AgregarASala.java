package com.cadena;

import java.util.ArrayList;
import javax.swing.DefaultListModel;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.HiloOutputSala;
import com.salas.Sala;
import com.vista.GUI_Lobby;
import com.vista.GUI_Sala;

public class AgregarASala extends ChainCliente{
	
	ArrayList<Sala> copiaSalasDisponibles;


	private GUI_Lobby lobbyGUI;
	
	public AgregarASala(ArrayList<Sala> copiaSalasDisponibles, GUI_Lobby lobbyGui) {
		this.copiaSalasDisponibles=copiaSalasDisponibles;
		this.lobbyGUI = lobbyGui;
	}

	@Override
	public void manejarPeticion(Mensaje mensaje) {

		if(mensaje.getComando().equals(Comandos.InvitacionASalaPublicaAceptada)||mensaje.getComando().equals(Comandos.InvitacionASalaPrivadaAceptada)) {
		System.out.println("AgregarASala Recibio: " + mensaje.getComando());
			String[] valores = mensaje.getInformacion().split(";");
			String clienteNuevo = valores[0];
			Integer idSala = Integer.valueOf(valores[1]);
			String nombreSala = valores[2];
			Sala salaModificada = null;
			GUI_Sala guiSala = null;
			boolean crearGUISala=true;
			
			for(Sala salaActual : copiaSalasDisponibles) {
				if(salaActual.getSalaID().equals(idSala)) {
					salaModificada = salaActual;
					if(salaActual.meterCliente(clienteNuevo)) {
						
						crearGUISala=salaActual.getSalaGui()==null?true:false;
						System.out.println("PARECE QUE LA SALA SIGUE CREADA");
						break;
					}
					
				}
			}
			
			if(crearGUISala) {
				DefaultListModel<String> modeloListaClientes = (DefaultListModel<String>) lobbyGUI.getListaClientesConectados().getModel();
				guiSala = new GUI_Sala(modeloListaClientes);


				guiSala.setTitleSala(nombreSala);
				guiSala.setSala(salaModificada);
				guiSala.setSalaID(idSala);
				
				HiloOutputSala hiloSala = new HiloOutputSala(guiSala,salaModificada);
				Thread thSala = new Thread(hiloSala);
				thSala.start();
				thSala.setName("Output Sala: "+ nombreSala);
				if(!salaModificada.esPrivada())
				lobbyGUI.agregarSala(nombreSala);
				salaModificada.setHilo(hiloSala);
				salaModificada.setSalaGui(guiSala);
				guiSala.setVisible(true);
				crearGUISala=false;
			}
				guiSala = salaModificada.getSalaGui();
				guiSala.limpiarListaClientes();
				for(int i=3 ;i<valores.length;i++) {
				guiSala.agregarCliente(valores[i]);
				}
			
			
		}
		else {this.siguiente.manejarPeticion(mensaje);}
	}

}
