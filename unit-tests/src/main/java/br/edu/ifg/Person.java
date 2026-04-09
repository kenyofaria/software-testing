package br.edu.ifg;

public class Person {
	
	private String name;
	private String area;
	private double salary;
	
	
	public Person(String name, String area, double salary) {
		super();
		this.name = name;
		this.area = area;
		this.salary = salary;
	}

	public double calculateTaxes() {
		if(area.equals("sell")) {
			return salary * 0.05;
		}else {
			return 0;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	
}
