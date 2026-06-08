package com.Esports.Msvcs_equipos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcsEquiposApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsvcsEquiposApplication.class, args);
	}
}
