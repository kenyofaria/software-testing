package br.edu.ifg.integration.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.gen5.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifg.entity.Cliente;
import br.edu.ifg.repository.ClienteRepository;
import br.edu.ifg.service.ClienteService;

class ClienteServiceIntegrationTest {

	private ClienteService clienteService;
	
	@BeforeEach
	public void init() {
		clienteService = new ClienteService(new ClienteRepository());
	}
	
	@AfterEach
	public void reset() {
		clienteService = null;
	}
	
	@Test
	void testCadastra() {
		Cliente cliente = new Cliente("kenyo", 'm', LocalDate.of(1980, Month.FEBRUARY, 25));
		clienteService.cadastra(cliente);
		List<Cliente> clientes = clienteService.listagem();
		assertTrue(clientes.contains(cliente));
	}

}
