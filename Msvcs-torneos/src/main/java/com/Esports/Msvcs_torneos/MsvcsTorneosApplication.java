package com.Esports.Msvcs_torneos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcsTorneosApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsvcsTorneosApplication.class, args);
	}
}
