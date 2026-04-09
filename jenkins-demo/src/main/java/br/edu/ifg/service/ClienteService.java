package br.edu.ifg.service;

import java.util.List;

import br.edu.ifg.entity.Cliente;
import br.edu.ifg.repository.ClienteRepository;

public class ClienteService {

	private ClienteRepository clienteRepository;


	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	
	public void cadastra(Cliente cliente) {
		if(cliente.getNome().trim().isEmpty()) //um test case que afirma e outro que nega
			throw new RuntimeException("o campo nome e obrigatorio"); //test case
		this.clienteRepository.add(cliente); //test case
	}
	
	public List<Cliente> listagem(){
		return this.clienteRepository.getList();
	}
}
