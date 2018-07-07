package com.salas;

import static com.Cliente.Cliente.nombreCliente;

import com.Cliente.EntradaSalida;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.vista.GUI_Lobby;

public class HiloOutputLobby implements Runnable{

	GUI_Lobby lobbyGui;
	
	EntradaSalida entradaSalida;
	boolean sigueCorriendo;
	public HiloOutputLobby(GUI_Lobby lobby_Gui){
	
		this.lobbyGui=lobby_Gui;
		this.entradaSalida=EntradaSalida.getInstance();
	}
		/** POR AHORA SOLO SE TIENE EN CUENTA EL LOBBY, SALAS A AGREGAR MAS ADELANTE
		* SE UTILIZA JTextPane ya que permite cambiar el estilo por linea(util a la hora de diferenciar si el mensaje es del mismo cliente
		*  o de otros
		*  StyledDocument trae el texto de la caja a ese objeto y permite su edicion desde ahi
		*  SimpleAtribbuteSet setea el atributo para esa parrafo(en estos casos las lineas de chat)
		*  
		*  Se imprime el mensaje en el JTextPane del cliente ya que es su mensaje y luego envia al server
		*  ESTE LO REPARTE A TODOS PERO CUANDO EL CLIENTE LO RECIBE VERIFICA SI FUE ENVIADO POR EL,CASO POSITIVO LO DESCARTA
		*  **/
	
	
	
	@Override
	public void run() {
		sigueCorriendo = true;
		StringBuilder texto;
			
		while(sigueCorriendo) {
		synchronized(this){
			try {
				
				wait();
			
			 // tengo en cuenta todos los GUI? o hago hilos separados?
				String textoAEnviar = lobbyGui.getChatTextBoxLobby().getText();
				if(!textoAEnviar.trim().equals("")) {
					texto = new StringBuilder();
				texto.append('\n');
				texto.append(nombreCliente + " : " );
				texto.append(textoAEnviar.trim());
				entradaSalida.escribirMensaje(new Mensaje(Comandos.MensajeASala,texto.toString(),-1));
				lobbyGui.getChatTextBoxLobby().setText("");
				}
					
			}catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
			
		}
	
	public void mandarMensaje(){
		synchronized(this){
			notify();
		}
	}
	
	public void setSigueCorriendo(boolean valor){
		this.sigueCorriendo = valor;
	}
}
