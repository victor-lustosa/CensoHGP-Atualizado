package br.com.unitins.censohgp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CensohgpApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CensohgpApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
	}
}
