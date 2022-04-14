package br.edu.ifg;

public class MathOperation {
	

	public double sum(double a, double b) {
		return a+b;
	}
	//cobertura -> 3 casos de testes
	public double sub(double a, double b) {
		if(a > Double.MAX_VALUE || b > Double.MAX_VALUE)
			throw new RuntimeException("o maior valor permitido para cada numero é: " + Double.MAX_VALUE);
		return a-b;
	}
	public double multi(double a, double b) {
		return a*b;
	}
	
	//complexidade ciclomatica
	public double div(double a, double b) {
		if(b == 0)
			throw new RuntimeException("segundo parametro deve ser diferente de zero");
		return a/b;
	}
	
}
