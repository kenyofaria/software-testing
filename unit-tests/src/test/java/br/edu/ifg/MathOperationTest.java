package br.edu.ifg;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MathOperationTest {

	@Test
	public void deveEfetuarSomaQuandoParametrosPositivos() {
		//setup ajuste do cenário
		//exercio da unidade under test
		//assert
		
		MathOperation operation = new MathOperation(); //class under test;  target class
		double valorEsperado = 5;
		int a = 3;
		int b = 2;
		double result = operation.sum(a, b); //method under test;   target method
		assertEquals(valorEsperado, result, 0);
	}

	@Test
	public void deveEfetuarSomaQuandoParametrosNegativos() {
		MathOperation operation = new MathOperation(); //class under test;  target class
		double valorEsperado = -5;
		double result = operation.sum(-3, -2); //method under test;   target method
		assertEquals(valorEsperado, result, 0);
	}
	
	@Test
	public void deveEfetuarSomaQuandoUmParametroForNegativo() {
		MathOperation operation = new MathOperation(); //class under test;  target class
		double valorEsperado = -1;
		double result = operation.sum(-3, 2); //method under test;   target method
		assertEquals(valorEsperado, result, 0);
	}
	
	
	@Test
	public void deveEfetuarSubQuandoParametrosPositivos() {
		MathOperation operation = new MathOperation(); //class under test;  target class
		double valorEsperado = 1;
		double result = operation.sub(3, 2); //method under test;   target method
		assertEquals(valorEsperado, result, 0);
	}
	
	@Test
	public void deveEfetuarMultiQuandoParametrosPositivos() {
		MathOperation operation = new MathOperation(); //class under test;  target class
		double valorEsperado = 6;
		double result = operation.multi(3, 2); //method under test;   target method
		assertEquals(valorEsperado, result, 0);
	}
	
	@Test
	public void deveEfetuarDivQuandoParametrosPositivos() {
		MathOperation operation = new MathOperation(); //class under test;  target class
		double valorEsperado = 5;
		double result = operation.div(10, 2); //method under test;   target method
		assertEquals(valorEsperado, result, 0);
	}
	
	@Test
	public void deveEfetuarDivQuandoPrimeiroParametroForZero() {
		MathOperation operation = new MathOperation(); //class under test;  target class
		double valorEsperado = 0;
		double result = operation.div(0, 2); //method under test;   target method
		assertEquals(valorEsperado, result, 0);
	}
	
	@Test
	public void naoDeveEfetuarDivQuandoSegundoParametroForZero() {
		MathOperation operation = new MathOperation(); //class under test;  target class
		RuntimeException thrown = assertThrows(RuntimeException.class, () -> operation.div(2, 0));
		assertTrue(thrown.getMessage().contains("segundo parametro deve ser diferente de zero"));
	}
}
