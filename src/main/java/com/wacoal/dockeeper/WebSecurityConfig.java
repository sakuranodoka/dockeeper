package com.wacoal.dockeeper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) 
            throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }

    /**
     *
     * @param auth
     * @throws Exception
     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .ldapAuthentication()
//                .userDnPatterns("uid={0}")
//                .groupSearchBase("ou=user")
//                .contextSource()
//                .url("ldap://10.11.9.135:389/dc=wacoal, dc=oc, dc=th")
//                .and()
//                .passwordCompare()
//                .passwordEncoder(new LdapShaPasswordEncoder())
//                .passwordAttribute("userPassword");
//    }
}
