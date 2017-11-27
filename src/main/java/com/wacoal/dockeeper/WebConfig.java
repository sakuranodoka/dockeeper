/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wacoal.dockeeper;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 *
 * @author Administrator
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    
    @Bean
    public MessageSource messageSource() {        
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();        
        messageSource.setBasename("i18n/messages");        
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    
    @Bean    
    public SessionLocaleResolver localeResolver() {        
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();        
        localeResolver.setDefaultLocale(new Locale("en", "US"));        
        return localeResolver;   
    }    
    
    @Bean    
    public LocaleChangeInterceptor localeChangeInterceptor() {        
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();        
        lci.setParamName("lang");        
        return lci;    
    }
    
    @Override    
    public void addInterceptors(InterceptorRegistry registry) {        
        registry.addInterceptor(localeChangeInterceptor());    
    }
    
    @Override    
    public void addViewControllers(ViewControllerRegistry registry) {        
        registry.addViewController("/home").setViewName("home");        
        registry.addViewController("/").setViewName("home");        
        registry.addViewController("/hello").setViewName("hello");        
        registry.addViewController("/login").setViewName("login");    
    }
}
