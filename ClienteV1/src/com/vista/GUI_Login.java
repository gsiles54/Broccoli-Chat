package com.vista;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.utilitarios.LoginHandler;

public class GUI_Login extends JFrame {

	private static final long serialVersionUID = 5818745717722164373L;
	private JPanel contentPane;
	private JTextField usuario_textField;
	private JPasswordField password_textField;
	private JButton btnNewButton;
	private JLabel lblEstado;
	private String username;
	private String password;
	private boolean boton=false;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Login loginGUI = new GUI_Login();
					loginGUI.setVisible(true);
					
					Socket socket=loginGUI.actualizarLabelEstadoConexion();
					loginGUI.manejarLogin(socket,loginGUI);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public GUI_Login()  {

		crearRecursosGUI();
		
	}
	
	private void manejarLogin(Socket socket, GUI_Login log) {
		LoginHandler loginHandler= new LoginHandler(socket, this);
		Thread tLoginHandler= new Thread(loginHandler);
		tLoginHandler.start();
	}

	
	protected Socket actualizarLabelEstadoConexion() {
		Socket socket=null;
		try {
			socket = new Socket("localhost",1234); //PARAMETRIZAR ESTO			
			if(socket.isConnected()) {
				lblEstado.setText("");
				lblEstado.setText("Estado: Servidor Online");
			}
		} catch (IOException e) {
			lblEstado.setText("");
			lblEstado.setText("Estado: Servidor Offline");

		}
		finally {
			return socket;
		}
	}
	
	private void crearRecursosGUI() {

		
		setResizable(false);
		setTitle("Broccoli Chat UNLAM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 274, 359);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem mntmConfiguracionServidor = new JMenuItem("Configuracion Servidor");
		mnMenu.add(mntmConfiguracionServidor);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mnMenu.add(mntmSalir);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnNewButton = new JButton("Iniciar Sesion");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(21, 144, 229, 107);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TO-DO VALIDACIONES BASICAS.

				username=usuario_textField.getText().trim();
				password=String.valueOf(password_textField.getPassword());
				boton=true;	
			}
		});
		contentPane.add(btnNewButton);
		
		usuario_textField = new JTextField();
		usuario_textField.setText("");
		usuario_textField.setBounds(58, 48, 131, 20);
		contentPane.add(usuario_textField);
		usuario_textField.setColumns(10);
		
		password_textField = new JPasswordField();
		password_textField.setBounds(58, 103, 131, 20);
		contentPane.add(password_textField);
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		lblNewLabel.setBounds(58, 23, 61, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Contraseña:");
		lblPassword.setBounds(58, 79, 142, 14);
		contentPane.add(lblPassword);
		
		lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(21, 281, 202, 14);
		contentPane.add(lblEstado);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public synchronized boolean isBoton() {
		return boton;
	}


	public void setBoton(boolean boton) {
		this.boton = boton;
	}
	
	
}
