package com.tests;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.cliente.Cliente;

public class Test_Cliente {
	
	Cliente cliente;
	Socket socket;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	
	@Before
	public void inicializar() throws IOException {

		
	}
	
	@Test
	public void test_getCliente() throws IOException {
		socket= Mockito.mock(Socket.class);
		
		FileOutputStream fos = new FileOutputStream("test1.tmp");
		oos= new ObjectOutputStream(fos);
		
		
		ObjectInputStream objectInputStream =
			    new ObjectInputStream(new FileInputStream(new File("object.data")));
		

		
		Mockito.when(socket.getInputStream()).thenReturn(objectInputStream);
		Mockito.when(socket.getOutputStream()).thenReturn(fos);
		cliente=new Cliente("Leo",socket);
		//Mockito.when(cliente.getNombre()).thenCallRealMethod();
		
		assertEquals("Leo", cliente.getNombre());
	}

}
