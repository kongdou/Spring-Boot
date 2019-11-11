package fun.deepsky.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public abstract class JsonWebTokenSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private JsonWebTokenAuthenticationProvider jsonWebTokenAuthenticationProvider; 
	
	@Autowired
	private JsonWebTokenAuthenticationFilter jsonWebTokenAuthenticationFilter;
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(jsonWebTokenAuthenticationProvider);
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().httpBasic().disable().formLogin().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
		
		setupAuthorization(http);

		http.addFilterBefore(jsonWebTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		super.configure(http);
	}

	protected abstract void setupAuthorization(HttpSecurity http) throws Exception ;
	
}
