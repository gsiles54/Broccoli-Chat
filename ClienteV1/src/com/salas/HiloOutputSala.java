package com.salas;


import com.Cliente.Cliente;
import com.Cliente.EntradaSalida;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.vista.GUI_Sala;

public class HiloOutputSala implements Runnable{
	GUI_Sala salaGUI;
	String cliente;
	EntradaSalida entradaSalida;
	Sala sala;
	public HiloOutputSala(String cliente,GUI_Sala _salaGUI,Sala sala) {
		this.salaGUI=_salaGUI;
		this.cliente=cliente;
		this.sala=sala;
		entradaSalida=EntradaSalida.getInstance();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean flag = true;
		StringBuilder texto;
		while(flag) {
			if(salaGUI.isChatBox()) { // tengo en cuenta todos los GUI? o hago hilos separados?
				salaGUI.setChatBox(false);
				texto = new StringBuilder();
				texto.append('\n');
				texto.append(cliente + " : " );
				texto.append(salaGUI.getChatTextBoxSala().getText());
				
				entradaSalida.escribirMensaje(new Mensaje(Comandos.MensajeASala,texto.toString(),sala.getSalaID()));
				salaGUI.getChatTextBoxSala().setText("");
			}
		}
	}

}
