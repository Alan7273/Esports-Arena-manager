package com.Esports.Msvcs_resultados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcsResultadosApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsvcsResultadosApplication.class, args);
	}
}
