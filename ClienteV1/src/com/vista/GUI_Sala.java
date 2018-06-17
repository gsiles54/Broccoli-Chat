package com.vista;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.salas.Sala;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JLabel;

public class GUI_Sala extends JFrame {

	private static final long serialVersionUID = 5818745717722164373L;
	private JPanel contentPane;
	private JTextField chatTextBoxSala;
	private boolean chatBox=false;
	private JTextPane chatSala;
	private GUI_Lobby guiLobby;
	JList<String> list;
	DefaultListModel<String> modelList;
	private String nombreSala;
	private Integer salaID;
	JLabel labelSalaID;
	private boolean salaPrivada;
	private Sala sala;
	
	public void setTitleSala(String nombre) {
		this.nombreSala=nombre;
		this.setTitle(nombre);
	}
	public void setSalaID(Integer salaID) {
		this.salaID=salaID;
		labelSalaID.setText("Sala ID : "+salaID);
	}
	public void setSala(Sala sala) {
		this.sala=sala;
	}
	public GUI_Sala()  {
		
		setResizable(true);
		getContentPane().setLayout(null);
		setBounds(100, 100, 804, 616);
		guiLobby=GUI_Lobby.guiLobby;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 472, 210);
		getContentPane().add(scrollPane);
		
		chatSala = new JTextPane();
		scrollPane.setViewportView(chatSala);
		
		chatTextBoxSala = new JTextField();
		chatTextBoxSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setChatBox(true);
			}
		});
		chatTextBoxSala.setBounds(10, 282, 386, 26);
		getContentPane().add(chatTextBoxSala);
		chatTextBoxSala.setColumns(10);
		
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.setBounds(411, 282, 71, 26);
		getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(492, 61, 113, 244);
		getContentPane().add(scrollPane_1);
		modelList = new DefaultListModel<String>();
		list = new JList<>(modelList);
		
		list.setFont(new Font("Source Sans Pro Semibold", Font.PLAIN, 16));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane_1.setViewportView(list);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(492, 61, 111, 242);
		getContentPane().add(textPane_1);
		
		labelSalaID= new JLabel();
		labelSalaID.setBounds(624, 11, 106, 14);
		getContentPane().add(labelSalaID);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu_1 = new JMenu("Menu");
		menuBar.add(mnMenu_1);
		
		JMenuItem mntmSalir_1 = new JMenuItem("Salir");
		mnMenu_1.add(mntmSalir_1);
		
		JMenu mnAbout = new JMenu("Sala");
		menuBar.add(mnAbout);
		
		JMenuItem mntmInvitarUsuario = new JMenuItem("Invitar Usuario");
		mntmInvitarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GUI_Invitar(sala);
				
			}
		});
		mnAbout.add(mntmInvitarUsuario);
	}

	
	private void crearRecursosGUI() {

		setResizable(false);
		setTitle("Sala @nombre");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 315, 598);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem mntmConfiguracionServidor = new JMenuItem("Configuracion Servidor");
		mnMenu.add(mntmConfiguracionServidor);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mnMenu.add(mntmSalir);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}


	public synchronized boolean isChatBox() {
		return chatBox;
	}


	public synchronized void setChatBox(boolean chatBox) {
		this.chatBox = chatBox;
	}
	
	public JTextField getChatTextBoxSala() {
		return chatTextBoxSala;
	}


	public void setChatTextBoxSala(JTextField chatTextBoxSala) {
		this.chatTextBoxSala = chatTextBoxSala;
	}


	public JTextPane getChatText() {
		return chatSala;
	}


	public void setChatText(JTextPane chatText) {
		this.chatSala = chatText;
	}

	public JTextPane getChatSala() {
		return chatSala;
	}
	public void agregarCliente(String nombre) {
		
		modelList.addElement(nombre);
	}
	public void quitarCliente(String nombre) {
		
	}
}
