package fun.deepsky.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 身份验证过
 * @author deepsky
 *
 */
@Component
public class JsonWebTokenAuthenticationFilter extends RequestHeaderAuthenticationFilter{

	public JsonWebTokenAuthenticationFilter() {
		this.setExceptionIfHeaderMissing(false);
		this.setPrincipalRequestHeader("Authorization");
	}
	
	@Autowired
	public void setAuthorizationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}
}
