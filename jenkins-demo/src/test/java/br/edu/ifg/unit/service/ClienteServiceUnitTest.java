package br.edu.ifg.unit.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.junit.gen5.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.edu.ifg.entity.Cliente;
import br.edu.ifg.repository.ClienteRepository;
import br.edu.ifg.service.ClienteService;

class ClienteServiceUnitTest {

	private ClienteService clienteService;
	private ClienteRepository mock;
	
	
	@BeforeEach
	public void init() {
		mock = Mockito.mock(ClienteRepository.class);
		clienteService = new ClienteService(mock);
	}
	
	@AfterEach
	public void reset() {
		mock = null;
		clienteService = null;
	}
	
	@Test
	void testCadastra() {
		Cliente cliente = new Cliente("kenyo", 'm', LocalDate.of(1980, Month.FEBRUARY, 25));
		Mockito.when(mock.getList()).thenReturn(Arrays.asList(cliente));
		clienteService.cadastra(cliente);
		List<Cliente> clientes = clienteService.listagem();
		assertTrue(clientes.contains(cliente));
	}

}
