package com.vista;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.Cliente.EntradaSalida;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI_Invitacion extends JFrame{
	
	EntradaSalida entradaSalida;
	
	public GUI_Invitacion(String nombreUsuario, String nombreSala, String idSala,boolean esPrivada) {
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel(nombreUsuario +" te ha invitado a la sala "+ nombreSala + "("+idSala+")");
		label.setBounds(24, 11, 284, 39);
		getContentPane().add(label);
		entradaSalida = EntradaSalida.getInstance();
		StringBuilder informacion = new StringBuilder();
		informacion.append(ControladorCliente.getInstance().getCliente());
		informacion.append(";");
		informacion.append(idSala);
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Mensaje mensaje;
				if(esPrivada) {
					mensaje = new Mensaje(Comandos.InvitacionASalaPrivadaAceptada,informacion.toString());
				}else {
					mensaje = new Mensaje(Comandos.InvitacionASalaPublicaAceptada,informacion.toString());
				}
				entradaSalida.escribirMensaje(mensaje);
			}
		});
		btnNewButton.setBounds(55, 77, 89, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Rechazar");
		btnNewButton_1.setBounds(199, 77, 89, 23);
		getContentPane().add(btnNewButton_1);
		this.setVisible(true);
	}
}
