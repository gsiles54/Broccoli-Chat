package com.vista;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JScrollPane;

import javax.swing.JTextField;

import javax.swing.border.EmptyBorder;


import com.Cliente.EntradaSalida;

import com.mensajes.Comandos;
import com.mensajes.Mensaje;

import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.SwingConstants;

public class GUI_Lobby extends JFrame {

	private static final long serialVersionUID = 7194023903108242857L;
	private JPanel contentPane;

	private JList<String> listaClientesConectados;
	private DefaultListModel<String> modelListaCliente;
	EntradaSalida entradaSalida;
	private JTextPane chatLobby=null;
	private JTextField chatTextBoxLobby;
	private boolean chatBox=false;
	static GUI_Lobby guiLobby;
	
	public GUI_Lobby(String _userName) {
		setResizable(false);
		
		
		configurarGUI();
		
		entradaSalida=EntradaSalida.getInstance();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

				int confirma=JOptionPane.showConfirmDialog(null,
				        "Realmente desea Salir?", "Realmente desea Salir?", JOptionPane.YES_NO_OPTION);
				if(confirma==0) {
				entradaSalida.escribirMensaje(new Mensaje(Comandos.LOGOUT, _userName));
				entradaSalida.cerrarEntradaSalida();
				dispose();
				} 
				
			}
		});
		
		guiLobby=this;
	}
	
	
	
	//------CONFIGURACIONES GUI---------------------
	private void configurarGUI() {
		setTitle("Broccoli Chat Lobby");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 804, 616);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		mnMenu.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		mnMenu.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnMenu);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");

		
		mntmSalir.setHorizontalAlignment(SwingConstants.CENTER);
		mnMenu.add(mntmSalir);
		
		JMenu mnSalas = new JMenu("Salas");
		mnSalas.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		mnSalas.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnSalas);
		
		JMenuItem mntmCrearSala = new JMenuItem("Crear Sala");
		
		mntmCrearSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					GUI_SolicitarNombreSala guiCrearSala = new GUI_SolicitarNombreSala();
					guiCrearSala.setVisible(true);
					
			}
		});
		
		mntmCrearSala.setHorizontalAlignment(SwingConstants.CENTER);
		mnSalas.add(mntmCrearSala);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 83, 179, 427);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(new Rectangle(0, 0, 500, 500));
		scrollPane_1.setBounds(0, 0, 179, 637);
		panel.add(scrollPane_1);
		
		modelListaCliente = new DefaultListModel<>();
		setListaClientesConectados(new JList<String>(modelListaCliente));
		scrollPane_1.setViewportView(getListaClientesConectados());
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(575, 83, 212, 427);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 212, 637);
		panel_1.add(scrollPane);
		
		JList<String> listaSalas = new JList<String>();
		listaSalas.setFont(new Font("Source Sans Pro Semibold", Font.PLAIN, 16));
		listaSalas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listaSalas);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(199, 83, 366, 427);
		contentPane.add(scrollPane_2);
		
		chatLobby = new JTextPane();
		chatLobby.setToolTipText("Sala publica. Todos podran leer lo que escribes aqui.");
		chatLobby.setFont(new Font("Source Sans Pro Semibold", Font.PLAIN, 16));
		
		scrollPane_2.setViewportView(chatLobby);
		
		chatTextBoxLobby = new JTextField();
		chatTextBoxLobby.setBounds(199, 521, 366, 33);
		chatTextBoxLobby.setToolTipText("Escriba su mensaje aqui");
		chatTextBoxLobby.setFont(new Font("Source Sans Pro Semibold", Font.PLAIN, 16));
		
		chatTextBoxLobby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setChatBox(true);
			}
		});
		contentPane.add(chatTextBoxLobby);
		chatTextBoxLobby.setColumns(10);
	}

	public synchronized JTextPane getChatLobby() {
		return chatLobby;
	}


	public synchronized JTextField getChatTextBoxLobby() {
		return chatTextBoxLobby;
	}



	public synchronized boolean isChatBox() {
		return chatBox;
	}

	public void setChatBox(boolean set) {
		this.chatBox=set;
	}

	public synchronized JList<String> getListaClientesConectados() {
		return listaClientesConectados;
	}

	public void setListaClientesConectados(JList<String> listaClientesConectados) {
		this.listaClientesConectados = listaClientesConectados;
		this.listaClientesConectados.setFont(new Font("Source Sans Pro Semibold", Font.PLAIN, 16));
		this.listaClientesConectados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
}
