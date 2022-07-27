//package com.eazybytes.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {
//	HttpMethod httpMethod;
//
//	/**
//	 * /myAccount - Secured /myBalance - Secured /myLoans - Secured /myCards -
//	 * Secured /notices - Not Secured /contact - Not Secured
//	 */
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// default
////		http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
////		http.formLogin();
////		http.httpBasic();
//
//		// customized
//
//		
////		  http.authorizeRequests().antMatchers("/myAccount").authenticated().
////		  antMatchers("/myBalance").authenticated()
////		  .antMatchers("/myLoans").authenticated().antMatchers("/myCards").
////		  authenticated().antMatchers("/notices")
////		  .permitAll().antMatchers("/contact").permitAll().and().formLogin().and().
////		  httpBasic();
//		 
//
//		
////		  http.authorizeRequests().regexMatchers("/secure/*").authenticated().
////		  regexMatchers("/notsecure/*").permitAll()
////		  .and().formLogin().and().httpBasic();
//		 
//		  http.authorizeRequests().antMatchers("/secure/**").authenticated().antMatchers("/notsecure/**").permitAll() .and().formLogin().and().httpBasic();
//		// all denial
//		/*
//		 * http.authorizeRequests((requests) -> requests.anyRequest().denyAll());
//		 * http.formLogin(); http.httpBasic();
//		 */
//		// permit All
//		/*
//		 * http.authorizeRequests((requests) -> requests.anyRequest().permitAll());
//		 * http.formLogin(); http.httpBasic();
//		 */
//	}
//
////	@Override
////	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////		auth.inMemoryAuthentication().withUser("admin").password("12345").authorities("admin").and().
////		withUser("User").password("12345").authorities("read").and().passwordEncoder(NoOpPasswordEncoder.getInstance());
////		
////	}
//
//	/*
//	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
//	 * Exception { 
//		 * InMemoryUserDetailsManager userDetailsServices = new
//		 * InMemoryUserDetailsManager(); UserDetails user =
//		 * User.withUsername("admin").password("12345").authorities("admin").build();
//		 * UserDetails user1 =
//		 * User.withUsername("user").password("12345").authorities("read").build();
//		 * 
//		 * userDetailsServices.createUser(user); userDetailsServices.createUser(user1);
//		 * auth.userDetailsService(userDetailsServices); 
//		 * }
//	 */
//
////	@Bean
////	public UserDetailsService userDetailsService(javax.sql.DataSource dataSource) {
////		return new JdbcUserDetailsManager(dataSource);
////	}
//
//	//@SuppressWarnings("deprecation")
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
////	@Bean
////	public PasswordEncoder passwordEncoder() {
////		return NoOpPasswordEncoder.getInstance();
////	}
//
//}**/



package com.eazybytes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.config.http.SessionCreationPolicy;
import com.eazybytes.filter.AuthoritiesLoggingAfterFilter;
import com.eazybytes.filter.AuthoritiesLoggingAtFilter;
import com.eazybytes.filter.JWTTokenGeneratorFilter;
import com.eazybytes.filter.JWTTokenValidatorFilter;
import com.eazybytes.filter.RequestValidationBeforeFilter;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ProjectSecurityConfig {

    /**
     * From Spring Security 5.7, the WebSecurityConfigurerAdapter is deprecated to encourage users
     * to move towards a component-based security configuration. It is recommended to create a bean
     * of type SecurityFilterChain for security related configurations.
     *
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
        cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }).and().csrf().disable()
        		//.csrf().ignoringAntMatchers("/contact").
                //csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((auth) -> auth
                        .antMatchers("/myAccount").hasRole("USER")
                        .antMatchers("/myBalance").hasAnyRole("USER","ADMIN")
                        .antMatchers("/myLoans").authenticated()
                        .antMatchers( "/myCards", "/user").authenticated()
                        .antMatchers("/notices", "/contact").permitAll()
                ).httpBasic(Customizer.withDefaults());
        return http.build();

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
