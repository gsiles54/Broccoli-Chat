package com.salas;


import static com.Cliente.Cliente.nombreCliente;
import com.Cliente.EntradaSalida;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.vista.GUI_Sala;

public class HiloOutputSala implements Runnable{
	GUI_Sala salaGUI;

	EntradaSalida entradaSalida;
	Sala sala;
	public HiloOutputSala(GUI_Sala _salaGUI,Sala sala) {
		this.salaGUI=_salaGUI;
		this.sala=sala;
		entradaSalida=EntradaSalida.getInstance();
	}
	@Override
	public void run() {
		boolean flag = true;
		StringBuilder texto;
		while(flag) {
			if(salaGUI.isChatBox()) { // tengo en cuenta todos los GUI? o hago hilos separados?
				salaGUI.setChatBox(false);
				texto = new StringBuilder();
				texto.append('\n');
				texto.append(nombreCliente + " : " );
				texto.append(salaGUI.getChatTextBoxSala().getText());
				
				entradaSalida.escribirMensaje(new Mensaje(Comandos.MensajeASala,texto.toString(),sala.getSalaID()));
				salaGUI.getChatTextBoxSala().setText("");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
