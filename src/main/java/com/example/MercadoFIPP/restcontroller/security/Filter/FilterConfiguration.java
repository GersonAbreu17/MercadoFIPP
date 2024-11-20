package com.example.MercadoFIPP.restcontroller.security.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean<AccessFilter> registrationBean() {
        FilterRegistrationBean<AccessFilter> register = new FilterRegistrationBean<>();
        register.setFilter(new AccessFilter());
        register.addUrlPatterns("/api/*"); // Defina aqui as URLs onde o filtro será aplicado
        return register;
    }
}

