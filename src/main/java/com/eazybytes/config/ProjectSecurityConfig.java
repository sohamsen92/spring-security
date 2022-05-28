package com.eazybytes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {
	HttpMethod httpMethod;

	/**
	 * /myAccount - Secured /myBalance - Secured /myLoans - Secured /myCards -
	 * Secured /notices - Not Secured /contact - Not Secured
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// default
//		http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
//		http.formLogin();
//		http.httpBasic();

		// customized

		
//		  http.authorizeRequests().antMatchers("/myAccount").authenticated().
//		  antMatchers("/myBalance").authenticated()
//		  .antMatchers("/myLoans").authenticated().antMatchers("/myCards").
//		  authenticated().antMatchers("/notices")
//		  .permitAll().antMatchers("/contact").permitAll().and().formLogin().and().
//		  httpBasic();
		 

		
//		  http.authorizeRequests().regexMatchers("/secure/*").authenticated().
//		  regexMatchers("/notsecure/*").permitAll()
//		  .and().formLogin().and().httpBasic();
		 
		  http.authorizeRequests().antMatchers("/secure/**").authenticated().antMatchers("/notsecure/**").permitAll() .and().formLogin().and().httpBasic();
		// all denial
		/*
		 * http.authorizeRequests((requests) -> requests.anyRequest().denyAll());
		 * http.formLogin(); http.httpBasic();
		 */
		// permit All
		/*
		 * http.authorizeRequests((requests) -> requests.anyRequest().permitAll());
		 * http.formLogin(); http.httpBasic();
		 */
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("admin").password("12345").authorities("admin").and().
//		withUser("User").password("12345").authorities("read").and().passwordEncoder(NoOpPasswordEncoder.getInstance());
//		
//	}

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { InMemoryUserDetailsManager userDetailsServices = new
	 * InMemoryUserDetailsManager(); UserDetails user =
	 * User.withUsername("admin").password("12345").authorities("admin").build();
	 * UserDetails user1 =
	 * User.withUsername("user").password("12345").authorities("read").build();
	 * 
	 * userDetailsServices.createUser(user); userDetailsServices.createUser(user1);
	 * auth.userDetailsService(userDetailsServices); }
	 */

//	@Bean
//	public UserDetailsService userDetailsService(javax.sql.DataSource dataSource) {
//		return new JdbcUserDetailsManager(dataSource);
//	}

	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
