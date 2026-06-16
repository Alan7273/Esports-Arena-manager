package com.Esports.Msvcs_resultados.config;

import feign.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion comun de los Feign clients de este microservicio.
 * El connect timeout y el read timeout se leen desde application.properties
 * (feign.client.config.default.connectTimeout / readTimeout) para tener
 * una unica fuente de verdad sobre el tiempo de espera maximo.
 */
@Configuration
public class FeignConfig {

    @Value("${feign.client.config.default.connectTimeout:5000}")
    private int connectTimeout;

    @Value("${feign.client.config.default.readTimeout:10000}")
    private int readTimeout;

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(
                connectTimeout,
                readTimeout
        );
    }
}
