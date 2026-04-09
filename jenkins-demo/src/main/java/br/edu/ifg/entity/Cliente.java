package br.edu.ifg.entity;

import java.time.LocalDate;

public class Cliente {

	private String nome;
	private char sexo;
	private LocalDate dataNascimento;
	
	public Cliente(String nome, char sexo, LocalDate dataNascimento) {
		super();
		this.nome = nome;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
	}
	public String getNome() {
		return nome;
	}
	public char getSexo() {
		return sexo;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
}
