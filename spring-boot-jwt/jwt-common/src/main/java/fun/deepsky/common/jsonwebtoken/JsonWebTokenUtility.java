package fun.deepsky.common.jsonwebtoken;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JsonWebTokenUtility {

	private SignatureAlgorithm signatureAlgorithm;
	private Key secretKey;
	
	public JsonWebTokenUtility() {
		signatureAlgorithm = SignatureAlgorithm.HS512;
		String encodedKey = "aslkdfa123123LLKJLKJKLJLLKJOOOI-00weqweqwe";
		secretKey = deserializeKey(encodedKey);
	}
	
	public AuthTokenDetailsDTO parseAndValidate(String token) {
		AuthTokenDetailsDTO authTokenDetailsDTO = null;
		Claims claims = Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
		String userId = claims.getSubject();
		String email = (String) claims.get("email");
		List<String> roleNames = (List<String>) claims.get("roles");
		Date expirationDate = claims.getExpiration();
		authTokenDetailsDTO = new AuthTokenDetailsDTO();
		authTokenDetailsDTO.setUserId(userId);
		authTokenDetailsDTO.setEmail(email);
		authTokenDetailsDTO.setRoleNames(roleNames);
		authTokenDetailsDTO.setExpirationDate(expirationDate);
		
		return authTokenDetailsDTO;
		
	}

	private Key deserializeKey(String encodedKey) {
		byte[] decoderKey = Base64.getDecoder().decode(encodedKey);
		Key key = new SecretKeySpec(decoderKey,getSignatureAlgorithm().getJcaName());
		return key;
	}

	public SignatureAlgorithm getSignatureAlgorithm() {
		return signatureAlgorithm;
	}

	public Key getSecretKey() {
		return secretKey;
	}
	
	
	
	
	
	
}
