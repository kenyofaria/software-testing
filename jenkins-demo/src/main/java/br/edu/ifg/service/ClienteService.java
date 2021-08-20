package br.edu.ifg.service;

import java.util.List;

import br.edu.ifg.entity.Cliente;
import br.edu.ifg.repository.ClienteRepository;

public class ClienteService {

	private ClienteRepository clienteRepository;


	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
		// TODO Auto-generated constructor stub
	}
	
	
	public void cadastra(Cliente cliente) {
		this.clienteRepository.add(cliente);
	}
	
	public List<Cliente> listagem(){
		return this.clienteRepository.getList();
	}
}
