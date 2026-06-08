package com.Esports.Msvcs_premios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcsPremiosApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsvcsPremiosApplication.class, args);
	}
}
