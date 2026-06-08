package com.Esports.Msvcs_usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcsUsuariosApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsvcsUsuariosApplication.class, args);
	}
}
