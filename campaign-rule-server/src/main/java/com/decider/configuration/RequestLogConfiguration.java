package com.decider.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class RequestLogConfiguration {
    @Autowired
    private final RequestFilterConfiguration requestFilterConfiguration ;

    @Bean
    public FilterRegistrationBean requestIdFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(requestFilterConfiguration);
        registrationBean.setOrder(Integer.MAX_VALUE);
        return registrationBean;
    }

}