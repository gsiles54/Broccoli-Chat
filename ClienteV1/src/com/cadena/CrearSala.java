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
			guiSala.setVisible(true);
			boolean esPrivada = p.getComando().equals(Comandos.SalaPrivCreadaExitosamente)?true:false;
			Sala nuevaSala = new Sala(p.getIDSala(),p.getInformacion(),esPrivada,guiSala);
			cl.agregarSala(nuevaSala);
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
