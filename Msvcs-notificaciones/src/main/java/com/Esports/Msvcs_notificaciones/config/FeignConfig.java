package com.Esports.Msvcs_notificaciones.config;

import feign.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.RequestInterceptor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.http.HttpHeaders;

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

    @Bean
    public RequestInterceptor reenviarToken() {
    return template -> {
        var attrs = RequestContextHolder.getRequestAttributes();
        if (attrs instanceof ServletRequestAttributes servlet) {
            String auth = servlet.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
            if (auth != null) {
                template.header(HttpHeaders.AUTHORIZATION, auth);   // reenvía el mismo Bearer
            }
        }
    };
}
}
