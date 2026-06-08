package com.Esports.Msvcs_rankings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcsRankingsApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsvcsRankingsApplication.class, args);
	}
}
