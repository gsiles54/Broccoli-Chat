package com.DB;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * El Usuario es con lo que cada persona se registra. Nombre y Password. No sabe nada de Sockets. <b>Solo se usa para Login</b>.
 * Este Usuario luego se convierte en Cliente del chat.
 * @author Maxi
 *
 */
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable{

	private static final long serialVersionUID = -575048319413665627L;
	
	@Id
	@Column(name = "Usuario_ID")
	int ID;
	
	@Column(name = "Nombre")
	String usuario;
	
	@Column(name = "Password")
	String password;
	
	public Usuario() {}

	
	public Usuario(int _ID, String usuario, String password) {
		ID=_ID;
		this.usuario = usuario;
		this.password = password;
	}

	public Usuario(String usuario, String password) {
		this.usuario = usuario;
		this.password = password;
		ID=-1;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getPassword() {
		return password;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
