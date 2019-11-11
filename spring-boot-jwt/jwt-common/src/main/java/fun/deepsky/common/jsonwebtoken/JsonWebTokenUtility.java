package fun.deepsky.common.jsonwebtoken;

import java.security.Key;
import java.util.Base64;

import io.jsonwebtoken.SignatureAlgorithm;

public class JsonWebTokenUtility {

	private SignatureAlgorithm signatureAlgorithm;
	private Key secretKey;
	
	public JsonWebTokenUtility() {
		signatureAlgorithm = SignatureAlgorithm.HS512;
		String encodedKey = "aslkdfa123123LLKJLKJKLJLLKJOOOI-00weqweqwe";
		secretKey = deserializeKey(encodedKey);
		
	}

	private Key deserializeKey(String encodedKey) {
		byte[] decoderKey = Base64.getDecoder().decode(encodedKey);
		return null;
	}
}
