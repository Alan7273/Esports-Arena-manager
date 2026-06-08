package com.Esports.Msvcs_inscripciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcsInscripcionesApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsvcsInscripcionesApplication.class, args);
	}
}
