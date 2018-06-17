package com.vista;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.Cliente.EntradaSalida;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.Sala;

import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI_Invitar extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6611151077592763893L;
	private DefaultListModel<String> modelList;
	private JList<String> listaUsuarios;
	private JPanel contentPane;
	private EntradaSalida entradaSalida;
	private String nombreSala;
	public GUI_Invitar(Sala sala) {
		this.nombreSala=sala.getNombre();
		setBounds(new Rectangle(150, 250, 400, 300));
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 23, 163, 202);
		entradaSalida = EntradaSalida.getInstance();
	
	    modelList = new DefaultListModel<>();
		listaUsuarios = new JList<>(modelList);
		listaUsuarios.setFont(new Font("Source Sans Pro Semibold", Font.PLAIN, 16));
		listaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaUsuarios.setBounds(new Rectangle(0, 0, 163, 202));
		
		agregarUsuariosLobby();
		scrollPane.add(listaUsuarios);
		scrollPane.setViewportView(listaUsuarios);
	
		contentPane.add(scrollPane);
		
		JButton btnNewButton = new JButton("Invitar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String nombre = listaUsuarios.getSelectedValue();
				
				if(nombre!=null) {
					StringBuilder informacion = new StringBuilder();
					informacion.append(nombre);
					informacion.append(';');
					informacion.append(nombreSala);
					informacion.append(';');
					informacion.append(sala.getSalaID());
					if(sala.esPrivada()) {
						entradaSalida.escribirMensaje(new Mensaje(Comandos.InvitarUsuarioSalaPrivada,informacion.toString()));
				
					}else {
						entradaSalida.escribirMensaje(new Mensaje(Comandos.InvitarUsuarioSalaPublica,informacion.toString()));
						
					}
					}
			}
		});
		btnNewButton.setBounds(194, 23, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnRefrescar = new JButton("Refrescar");
		btnRefrescar.setBounds(194, 60, 89, 23);
		contentPane.add(btnRefrescar);
		this.setVisible(true);
		
	}
	
	public void agregarUsuariosLobby() {
		GUI_Lobby guiLobby = GUI_Lobby.guiLobby;
		listaUsuarios=guiLobby.getListaClientesConectados();
	}
}
