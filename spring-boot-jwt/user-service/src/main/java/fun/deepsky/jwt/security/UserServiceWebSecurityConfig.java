package fun.deepsky.jwt.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import fun.deepsky.common.security.JsonWebTokenSecurityConfig;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "fun.deepsky.common.security")
public class UserServiceWebSecurityConfig extends JsonWebTokenSecurityConfig{

	@Override
	protected void setupAuthorization(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/authenticate").permitAll().anyRequest().authenticated();
	}

}
