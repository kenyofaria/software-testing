package br.edu.ifg.repository;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifg.entity.Cliente;

public class ClienteRepository {

	private static List<Cliente> clientes = new ArrayList<Cliente>();
	
	public void add(Cliente cliente) {
		clientes.add(cliente);
	}
	
	public List<Cliente> getList(){
		return clientes;
	}
}
