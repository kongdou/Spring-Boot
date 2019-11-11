package fun.deepsky.common.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import fun.deepsky.common.jsonwebtoken.AuthTokenDetailsDTO;
import fun.deepsky.common.jsonwebtoken.JsonWebTokenUtility;

@Component
public class JsonWebTokenAuthenticationProvider implements AuthenticationProvider{

	private JsonWebTokenUtility jsonWebTokenUtility = new JsonWebTokenUtility();
	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Authentication authenticationUser = null;
		
		if(authentication.getClass().isAssignableFrom(PreAuthenticatedAuthenticationToken.class)
				&& authentication.getPrincipal() != null) {
			String tokenHeader = (String) authentication.getPrincipal();
			UserDetails userDetails = pareseToken(tokenHeader);
			if(userDetails != null) {
				authenticationUser = new JsonWebTokenAuthentication(userDetails, tokenHeader);
			}
		}else {
			authenticationUser = authentication;
		}
		return authenticationUser;
	}

	private UserDetails pareseToken(String tokenHeader) {
		UserDetails principal = null;
		AuthTokenDetailsDTO authTokenDetailsDTO = jsonWebTokenUtility.parseAndValidate(tokenHeader);
		if(authTokenDetailsDTO != null) {
			List<GrantedAuthority> authorities = authTokenDetailsDTO.getRoleNames().stream().map(roleName -> new SimpleGrantedAuthority(roleName)).collect(Collectors.toList());
			principal = new User(authTokenDetailsDTO.getEmail(), "", authorities);
		}
		return principal;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(PreAuthenticatedAuthenticationToken.class) || authentication.isAssignableFrom(JsonWebTokenAuthentication.class);
	}

}
