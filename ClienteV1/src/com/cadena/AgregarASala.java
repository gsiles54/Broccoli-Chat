package com.cadena;

import java.util.List;


import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.HiloOutputSala;
import com.salas.Sala;
import com.vista.ControladorCliente;
import com.vista.GUI_Sala;

public class AgregarASala extends ChainCliente{

	@Override
	public void manejarPeticion(Mensaje msj) {
		// TODO Auto-generated method stub
		if(msj.getComando().equals(Comandos.InvitacionASalaPublicaAceptada)||msj.getComando().equals(Comandos.InvitacionASalaPrivadaAceptada)) {
			cl = ControladorCliente.getInstance();
			List<Sala> salas = cl.getCopiaSalasDisponibles();
			String[] valores = msj.getInformacion().split(";");
			String clienteNuevo = valores[0];
			String idSala = valores[1];
			String nombreSala = valores[2];
			boolean crearSala=true;
			for(Sala salaActual : salas) {
				if(salaActual.getSalaID().equals(Integer.valueOf(idSala))) {
					if(salaActual.meterCliente(clienteNuevo)) {
						salaActual.getSalaGui().agregarCliente(clienteNuevo);
						crearSala=false;
					}
					
				}
			}
			
			if(crearSala) {
				boolean esPrivada = msj.getComando().equals(Comandos.InvitacionASalaPublicaAceptada)?false:true;
				
				GUI_Sala guiSala = new GUI_Sala();
				Sala nuevaSala = new Sala(Integer.valueOf(idSala),nombreSala,esPrivada,guiSala);
				cl.agregarSala(nuevaSala);
				guiSala.setTitleSala(nombreSala);
				guiSala.setSalaID(Integer.valueOf(idSala));
				for(int i=3 ;i<valores.length;i++) {
					guiSala.agregarCliente(valores[i]);
				}
				HiloOutputSala hiloSala = new HiloOutputSala(cl.getCliente(),guiSala,nuevaSala);
				Thread thSala = new Thread(hiloSala);
				thSala.start();
			
				guiSala.setVisible(true);
			}
		}
		else {
			siguiente.manejarPeticion(msj);
			
		}
	}

}
