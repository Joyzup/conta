package com.factory.contabancaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContabancariaApplication {

	//Para evitar duplicação com meu dados locais.
	// Atenção que eu alterei o nome e senha do BD no application.properties 😉😜👀

	public static void main(String[] args) {
		SpringApplication.run(ContabancariaApplication.class, args);
	}

}
