package com.cadena;


import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.HiloOutputSala;
import com.salas.Sala;
import com.vista.ControladorCliente;
import com.vista.GUI_Sala;

public class CrearSala extends ChainCliente{
	
	
	public CrearSala() {
		
	}

	@Override
	public void manejarPeticion(Mensaje p) {
		
		if(p.getComando().equals(Comandos.SalaPrivCreadaExitosamente)||p.getComando().equals(Comandos.SalaPubCreadaExitosamente)) {
			cl = ControladorCliente.getInstance();
			GUI_Sala guiSala = new GUI_Sala();
			String valores [] = p.getInformacion().split(";");
			String nombre = valores[0];
			Integer idSala = Integer.valueOf(valores[1]);

			guiSala.setVisible(true);
			guiSala.agregarCliente(cl.getCliente());
			boolean esPrivada = p.getComando().equals(Comandos.SalaPrivCreadaExitosamente)?true:false;
			Sala nuevaSala = new Sala(idSala,nombre,esPrivada,guiSala);
			cl.agregarSala(nuevaSala);
			guiSala.setSala(nuevaSala);
			nuevaSala.meterCliente(cl.getCliente());
			HiloOutputSala hiloSala = new HiloOutputSala(cl.getCliente(),guiSala,nuevaSala);
			Thread thSala = new Thread(hiloSala);
			thSala.start();
		}
		else {
		
			siguiente.manejarPeticion(p);
		}
	}

}
