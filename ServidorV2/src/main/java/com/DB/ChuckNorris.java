package com.DB;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CHUCK")
public class ChuckNorris implements Serializable {

	private static final long serialVersionUID = -5153794647898719855L;
	
	@Id
	@Column(name="Chuck_ID")
	private int ID;
	
	@Column(name="FACT")
	private String frase;
	
	public ChuckNorris() {}

	public ChuckNorris(int iD, String frase) {
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
