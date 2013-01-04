package it.dariofabbri.controlloaccessirai.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "utenti")
public class Utente {

	@Id
	@Column(name="Matricola")
	private Integer matricola;
	
	@Column(name="Username")
	private String username;
	
	@Column(name="Digest")
	private String digest;
	
	@Column(name="Salt")
	private String salt;
	
	@Column(name="Iterations")
	private Integer iterations;

	@Column(name="Nome")
	private String nome;
	
	@Column(name="Cognome")
	private String cognome;
	
	@Column(name="TipoAccount")
	private String tipoAccount;

	public Integer getMatricola() {
		return matricola;
	}

	public void setMatricola(Integer matricola) {
		this.matricola = matricola;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getIterations() {
		return iterations;
	}

	public void setIterations(Integer iterations) {
		this.iterations = iterations;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getTipoAccount() {
		return tipoAccount;
	}

	public void setTipoAccount(String tipoAccount) {
		this.tipoAccount = tipoAccount;
	}
}