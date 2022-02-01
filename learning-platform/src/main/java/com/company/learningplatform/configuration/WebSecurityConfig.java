package com.company.learningplatform.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.company.learningplatform.constant.SecurityConstant;
import com.company.learningplatform.security.AppAuthenticationEntryPoint;
import com.company.learningplatform.security.filter.AppAccessDeniedHandler;
import com.company.learningplatform.security.filter.AppAuthorizationFilter;
import com.company.learningplatform.service.UserService;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	private UserService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private AppAccessDeniedHandler accesDeniedHandler;

	@Autowired
	private AppAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private AppAuthorizationFilter authorizationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests().antMatchers(SecurityConstant.PUBLIC_URLS)
				.permitAll()
				.anyRequest().authenticated()
				.and()
				.exceptionHandling().accessDeniedHandler(accesDeniedHandler)
				.authenticationEntryPoint(authenticationEntryPoint)
				.and()
				.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

		http.csrf().disable();
		http.headers().frameOptions().disable();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(bCryptPasswordEncoder);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManager();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource()
	{

		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

}
