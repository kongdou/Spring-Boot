package fun.deepsky.jwt.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String email;
	
	@ManyToMany
	@JoinTable(name = "user_roles",joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id"))
	private Set<Role> roles;
	
	private int passwordHash;
	
	protected User() {
	}

	public User(String email, String password) {
		this.email = email;
		setPassword(password);
	}

	public User(String email, String password, Set<Role> roles) {
		this.email = email;
		setPassword(password);
		this.roles = roles;
	}

	public void setPassword(String password) {
		int hc = password.hashCode();
		setPasswordHash(hc);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public int getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(int passwordHash) {
		this.passwordHash = passwordHash;
	}
	
}
