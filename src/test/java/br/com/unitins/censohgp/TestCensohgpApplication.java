package br.com.unitins.censohgp;

import org.springframework.boot.SpringApplication;

public class TestCensohgpApplication {

	public static void main(String[] args) {
		SpringApplication.from(CensohgpApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
