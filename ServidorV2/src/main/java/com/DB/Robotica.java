package com.DB;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Robotica")
public class Robotica implements Serializable {

	private static final long serialVersionUID = -371128921165058773L;
	
	@Id
	@Column(name="Robotica_ID")
	private int ID;
	
	@Column(name="Fact")
	private String frase;
	
	public Robotica() {}

	public Robotica(int iD, String frase) {
		super();
		ID = iD;
		this.frase = frase;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getFrase() {
		return frase;
	}

	public void setFrase(String frase) {
		this.frase = frase;
	}
}
