package com.Cliente;

public class Cliente {

	String username;
	Cliente cliente;
	
	public Cliente(String _username){
		this.username=_username;
		cliente=this;
	}
	
	public String getNombre() {
		return username;
	}
	
}
