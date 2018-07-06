package com.cadena;

import java.util.ArrayList;

import static com.Cliente.Cliente.nombreCliente;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.Sala;

import com.vista.GUI_Sala;

public class RefrescarCliente extends ChainCliente {

	private ArrayList<Sala> copiaSalas;

	public RefrescarCliente(ArrayList<Sala> copiaSalasDisponibles) {
		this.copiaSalas = copiaSalasDisponibles;
	}

	@Override
	public void manejarPeticion(Mensaje msj) {

		if (msj.getComando().equals(Comandos.RefrescarClientes)) {
			System.out.println("Refresh Recibio: " + msj.getComando());
			String[] valores = msj.getInformacion().split(";");
			for (Sala salaActual : copiaSalas) {
				GUI_Sala guiSala = salaActual.getSalaGui();
				if (guiSala != null) {
					guiSala.getModeloClientesEnLobby().clear();
					for (int i = 0; i < valores.length; i++) {
							guiSala.getModeloClientesEnLobby().addElement(valores[i]);
					}
					for(String clienteActual : salaActual.getClientesEnSala()){ 
						while(guiSala.getModeloClientesEnLobby().removeElement(clienteActual));
					}
					
				}

			}

		} else {
			siguiente.manejarPeticion(msj);
		}
	}

}
