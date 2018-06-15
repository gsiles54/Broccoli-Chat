package com.vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class GUI_ConfigurarIP extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_ConfigurarIP frame = new GUI_ConfigurarIP();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI_ConfigurarIP() {
		setTitle("Configuracion");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 282, 230);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(86, 49, 145, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(86, 96, 145, 20);
		contentPane.add(textField_1);
		
		JLabel lblIp = new JLabel("IP: ");
		lblIp.setBounds(30, 52, 46, 14);
		contentPane.add(lblIp);
		
		JLabel lblPuerto = new JLabel("Puerto: ");
		lblPuerto.setBounds(30, 102, 67, 14);
		contentPane.add(lblPuerto);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(149, 137, 82, 23);
		contentPane.add(btnOk);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(30, 137, 82, 23);
		contentPane.add(btnCancelar);
	}
}
