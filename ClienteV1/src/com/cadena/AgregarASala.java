package com.cadena;

import java.util.ArrayList;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.HiloOutputSala;
import com.salas.Sala;
import com.vista.GUI_Sala;

public class AgregarASala extends ChainCliente{
	
	ArrayList<Sala> copiaSalasDisponibles;
	String nombreCliente;
	
	
	public AgregarASala(ArrayList<Sala> copiaSalasDisponibles, String nombreCliente) {
		this.copiaSalasDisponibles=copiaSalasDisponibles;
		this.nombreCliente=nombreCliente;
	}

	@Override
	public void manejarPeticion(Mensaje mensaje) {

		if(mensaje.getComando().equals(Comandos.InvitacionASalaPublicaAceptada)||mensaje.getComando().equals(Comandos.InvitacionASalaPrivadaAceptada)) {
			String[] valores = mensaje.getInformacion().split(";");
			String clienteNuevo = valores[0];
			String idSala = valores[1];
			String nombreSala = valores[2];
			boolean crearSala=true;
			for(Sala salaActual : copiaSalasDisponibles) {
				if(salaActual.getSalaID().equals(Integer.valueOf(idSala))) {
					if(salaActual.meterCliente(clienteNuevo)) {
						salaActual.getSalaGui().agregarCliente(clienteNuevo);
						crearSala=false;
					}
					
				}
			}
			
			if(crearSala) {
				boolean esPrivada = mensaje.getComando().equals(Comandos.InvitacionASalaPublicaAceptada)?false:true;
				
				GUI_Sala guiSala = new GUI_Sala();
				Sala nuevaSala = new Sala(Integer.valueOf(idSala),nombreSala,esPrivada,guiSala);
				copiaSalasDisponibles.add(nuevaSala);
				guiSala.setTitleSala(nombreSala);
				guiSala.setSalaID(Integer.valueOf(idSala));
				for(int i=3 ;i<valores.length;i++) {
					guiSala.agregarCliente(valores[i]);
				}
				HiloOutputSala hiloSala = new HiloOutputSala(nombreCliente,guiSala,nuevaSala);
				Thread thSala = new Thread(hiloSala);
				thSala.start();
			
				guiSala.setVisible(true);
			}
		}
		else {System.out.println("Agregar mas manejadores. AgregarASala fue ultimo eslabon, el comando fue: "+mensaje.getComando());;}
	}

}
