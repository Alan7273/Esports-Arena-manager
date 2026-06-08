package com.Esports.Msvcs_sanciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcsSancionesApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsvcsSancionesApplication.class, args);
	}
}
