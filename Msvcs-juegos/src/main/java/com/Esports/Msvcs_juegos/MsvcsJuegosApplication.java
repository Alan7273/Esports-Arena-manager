package com.Esports.Msvcs_juegos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcsJuegosApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsvcsJuegosApplication.class, args);
	}
}
