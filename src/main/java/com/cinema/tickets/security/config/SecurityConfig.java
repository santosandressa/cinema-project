package com.cinema.tickets.security.config;

import com.cinema.tickets.security.filter.CustomAuthenticationFilter;
import com.cinema.tickets.security.filter.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private static final String[] PUBLIC_MATCHERS = {
            "/api/v1/cliente/cadastrar",
            "/api/v1/login",
            "/api/v1/cliente/token/refresh",
    };

    private static final String[] PUBLIC_MATCHERS_SWAGGER = {
            "/swagger-ui/**",
            "/swagger-ui/index.html#",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v1/api-docs",
            "/v1/api-docs/swagger-config"
    };

    private static final String[] PUBLIC_MATCHERS_GET = {
            "/api/v1/poltronas/**",
            "/api/v1/filmes/**",
    };

    private static final String[] PRIVATE_MATCHERS_ADMIN = {
            "/api/v1/cliente/deletar/{id}",
            "/api/v1/cliente/{id}",
            "api/v1/cliente/listar",
            "api/v1/filme/cadastrar",
            "api/v1/filme/deletar/{id}",
            "api/v1/filme/atualizar/{id}",
            "api/v1/poltrona/cadastrar",
            "api/v1/poltrona/atualizar/{id}",
            "api/v1/sala/cadastrar",
            "api/v1/sala/atualizar/{id}",
    };

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());

        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/cliente/login");

        http.authorizeRequests()
                .antMatchers(GET, PUBLIC_MATCHERS_GET).permitAll()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers(POST, PRIVATE_MATCHERS_ADMIN).hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .cors().configurationSource(corsConfigurationSource());

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(PUBLIC_MATCHERS_SWAGGER);
    }


}
