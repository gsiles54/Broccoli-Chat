package com.salas;

import static com.Cliente.Cliente.nombreCliente;
import com.Cliente.EntradaSalida;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.vista.GUI_Sala;

public class HiloOutputSala implements Runnable {

	GUI_Sala salaGUI;
	EntradaSalida entradaSalida;
	Sala sala;
	private boolean sigueCorriendo;

	public HiloOutputSala(GUI_Sala _salaGUI, Sala sala) {
		this.salaGUI = _salaGUI;
		this.sala = sala;
		entradaSalida = EntradaSalida.getInstance();
	}

	@Override
	public void run() {
		sigueCorriendo = true;
		StringBuilder texto;
		while (sigueCorriendo) {

			synchronized (this) {
				try {
					wait();

					 // tengo en cuenta todos los GUI?
												// o
												// hago hilos separados?
					
						
						
						String textoIngresado =salaGUI.getChatTextBoxSala().getText().trim();
						if(!textoIngresado.equals("")) {
						texto = new StringBuilder();
						texto.append('\n');
						texto.append(nombreCliente + " : ");
						
						StringBuilder textoAEnviar = new StringBuilder(textoIngresado);
						
						   int  i = 30;
						   if(textoAEnviar.length()>30) {
							   while (i<textoAEnviar.length()) {
								  
					        	textoAEnviar.insert(i, '\n'); 
					        	i+=30;
					        }
						   }
					      
					     texto.append(textoAEnviar.toString());  
						entradaSalida.escribirMensaje(
								new Mensaje(Comandos.MensajeASala, texto.toString(), sala.getSalaID()));
						salaGUI.getChatTextBoxSala().setText("");
						}
						

					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}

	public boolean isSigueCorriendo() {
		return sigueCorriendo;
	}

	public void setSigueCorriendo(boolean sigueCorriendo) {
		this.sigueCorriendo = sigueCorriendo;
	}
	
	public void mandarMensaje(){
		synchronized(this){
			notify();
		}
	}

}