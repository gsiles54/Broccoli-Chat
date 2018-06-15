package com.DB;
/**
 * El Usuario es con lo que cada persona se registra. Nombre y Password. No sabe nada de Sockets. <b>Solo se usa para Login</b>.
 * Este Usuario luego se convierte en Cliente del chat.
 * @author Maxi
 *
 */
public class Usuario {

	String usuario;
	String password;

	public Usuario(String usuario, String password) {
		super();
		this.usuario = usuario;
		this.password = password;
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
}
