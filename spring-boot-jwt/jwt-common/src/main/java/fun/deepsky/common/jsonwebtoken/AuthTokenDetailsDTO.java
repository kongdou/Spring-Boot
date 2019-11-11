package fun.deepsky.common.jsonwebtoken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthTokenDetailsDTO {
	private String userId;
	private String email;
	private List<String> roleNames = new ArrayList<>();
	private Date expirationDate;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	} 
	
}
