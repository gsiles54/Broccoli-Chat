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

import com.Cliente.EntradaSalida;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.HiloOutputSala;
import com.salas.Sala;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;

import static com.Cliente.Cliente.nombreCliente;

public class GUI_Sala extends JFrame {

	private static final long serialVersionUID = 5818745717722164373L;
	private JPanel contentPane;
	private JTextField chatTextBoxSala;
	
	private JTextPane chatSala;

	JList<String> list;
	DefaultListModel<String> modeloClientesEnSala;
	private DefaultListModel<String> modeloClientesEnLobby;
	public DefaultListModel<String> getModeloClientesEnLobby() {
		return modeloClientesEnLobby;
	}
	private String nombreSala;
	private Integer salaID;
	JLabel labelSalaID;
	private boolean salaPrivada;
	private Sala sala;
	

	public GUI_Sala(DefaultListModel<String> modeloListaClientes)  {
		addWindowListener(new WindowAdapter() {
		
			@Override
			public void windowClosing(WindowEvent e) {
				sala.getHilo().setSigueCorriendo(false);
				StringBuilder informacion = new StringBuilder();
				informacion.append(nombreCliente);
				informacion.append(';');
				informacion.append(nombreSala);
				informacion.append(';');
				informacion.append(salaID);
				informacion.append(';');
				informacion.append(-1);
				if(!sala.isConversacion()){
				EntradaSalida.getInstance().escribirMensaje(new Mensaje(Comandos.ClienteDejandoSala,informacion.toString()));
				
				sala.sacarCliente(nombreCliente);
				System.out.println("SE CERROOOOOOOOOOOOOOOOOO");
				}else{
					
					EntradaSalida.getInstance().escribirMensaje(new Mensaje(Comandos.ClienteDejandoConver,informacion.toString()));
					
					sala.setSalaGui(null);
				}
				
			}
		});
		
		copiarClientes(modeloListaClientes);
		setResizable(true);
		getContentPane().setLayout(null);
		setBounds(100, 100, 631, 373);
	
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 472, 210);
		getContentPane().add(scrollPane);
		
		chatSala = new JTextPane();
		scrollPane.setViewportView(chatSala);
		
		chatTextBoxSala = new JTextField();
		chatTextBoxSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			HiloOutputSala outputSala = sala.getHilo();
			outputSala.mandarMensaje();
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
		modeloClientesEnSala = new DefaultListModel<String>();
		list = new JList<>(modeloClientesEnSala);
		
		list.setFont(new Font("Source Sans Pro Semibold", Font.PLAIN, 16));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane_1.setViewportView(list);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(492, 61, 111, 242);
		getContentPane().add(textPane_1);
		
		labelSalaID= new JLabel();
		labelSalaID.setBounds(499, 11, 106, 14);
		getContentPane().add(labelSalaID);
		
		JLabel lblUsuarios = new JLabel("Usuarios: ");
		lblUsuarios.setBounds(492, 39, 78, 14);
		getContentPane().add(lblUsuarios);
		
		JLabel lblUsuario = new JLabel("USUARIO: "+ nombreCliente);
		lblUsuario.setBounds(10, 11, 78, 14);
		getContentPane().add(lblUsuario);
		
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
				new GUI_Invitar(sala, modeloClientesEnLobby);
				
			}
		});
		mnAbout.add(mntmInvitarUsuario);
	}


	private void copiarClientes(DefaultListModel<String> modeloListaClientes) {
		this.modeloClientesEnLobby = new DefaultListModel<String>();
		for(int i = 0 ; i<modeloListaClientes.size(); i++){
			modeloClientesEnLobby.addElement(modeloListaClientes.getElementAt(i));
		}
		modeloClientesEnLobby.removeElement(nombreCliente);
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
		
		modeloClientesEnSala.addElement(nombre);
	}
	/**
	 * 
	 * @param nombre
	 */
	public void quitarCliente(String nombre) {
		modeloClientesEnSala.removeElement(nombre);
	}
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
	public void limpiarListaClientes(){
		modeloClientesEnSala.removeAllElements();
	}
}
