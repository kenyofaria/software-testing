package br.edu.ifg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PersonTest {

	@Test
	void deveCalcularTaxasQuandoPessoaVendedor() {
		
		Person person = new Person("kenyo", "sell", 10000);
		double taxes = person.calculateTaxes();
		assertEquals(500, taxes);
	}

	@Test
	void nadoDeveCalcularTaxasQuandoPessoaNaoEVendedor() {
		
		Person person = new Person("kenyo", "financeiro", 10000);
		double taxes = person.calculateTaxes();
		assertEquals(0.0, taxes);
	}
}
