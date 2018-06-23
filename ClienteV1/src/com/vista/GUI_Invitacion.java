package com.vista;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.Cliente.EntradaSalida;
import static com.Cliente.Cliente.nombreCliente;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class GUI_Invitacion extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4924515329551136658L;
	EntradaSalida entradaSalida;
	
	public GUI_Invitacion(String nombreUsuario, String nombreSala, String idSala,boolean esPrivada) {
		setBounds(new Rectangle(100, 100, 400, 200));
		setMinimumSize(new Dimension(400, 200));
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel(nombreUsuario +" te ha invitado a la sala "+ nombreSala + "("+idSala+")");
		label.setBounds(24, 11, 311, 39);
		getContentPane().add(label);
		entradaSalida = EntradaSalida.getInstance();
		StringBuilder informacion = new StringBuilder();
		informacion.append(nombreCliente);
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
				dispose();
			}
		});
		btnNewButton.setBounds(55, 77, 89, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Rechazar");
		btnNewButton_1.setBounds(199, 77, 89, 23);
		getContentPane().add(btnNewButton_1);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		this.setVisible(true);
	}
}
