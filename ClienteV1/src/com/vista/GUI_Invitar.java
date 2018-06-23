package com.vista;

import static com.Cliente.Cliente.nombreCliente;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import com.Cliente.EntradaSalida;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.salas.Sala;

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
	
	public GUI_Invitar(Sala sala,DefaultListModel<String> modeloListaClientes) {
		
	
		this.nombreSala=sala.getNombreSala();
		setBounds(new Rectangle(150, 250, 400, 300));
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 23, 163, 202);
		entradaSalida = EntradaSalida.getInstance();
	
	    modelList = modeloListaClientes;
	    this.listaUsuarios = new JList<>(modelList);
	    this.listaUsuarios.setFont(new Font("Source Sans Pro Semibold", Font.PLAIN, 16));
	    this.listaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    this.listaUsuarios.setBounds(new Rectangle(0, 0, 163, 202));
		
	
		scrollPane.add(this.listaUsuarios);
		scrollPane.setViewportView(this.listaUsuarios);
	
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
					informacion.append(';');
					informacion.append(nombreCliente);
					if(sala.esPrivada()) {
						entradaSalida.escribirMensaje(new Mensaje(Comandos.InvitarUsuarioSalaPrivada,informacion.toString()));
				
					}else {
						entradaSalida.escribirMensaje(new Mensaje(Comandos.InvitarUsuarioSalaPublica,informacion.toString()));
						
					}
					}
				JButton s=(JButton)arg0.getSource(); 
				JFrame jf= (JFrame) SwingUtilities.getRoot(s);
				jf.setVisible(false);
			}
			
			
		});
		btnNewButton.setBounds(194, 23, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnRefrescar = new JButton("Refrescar");
		btnRefrescar.setBounds(194, 60, 89, 23);
		contentPane.add(btnRefrescar);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		this.setVisible(true);
		
	}
	

}
