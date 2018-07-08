package com.tests;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.cliente.Cliente;

public class Test_Cliente {
	
	
	
	@Before
	public void inicializar() throws IOException {
	}
	
	@Test
	public void test_getCliente() throws IOException {
		Cliente cliente=new Cliente("Leo",null);
		assertEquals("Leo", cliente.getNombre());
	}

}
