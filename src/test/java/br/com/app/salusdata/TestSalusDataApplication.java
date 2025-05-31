package br.com.app.salusdata;

import org.springframework.boot.SpringApplication;

public class TestSalusDataApplication {

	public static void main(String[] args) {
		SpringApplication.from(SalusDataApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
