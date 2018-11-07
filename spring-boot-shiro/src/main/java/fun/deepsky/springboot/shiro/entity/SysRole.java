package fun.deepsky.springboot.shiro.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class SysRole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6935038969967922337L;

	@Id
	@GeneratedValue
	private Integer id; 
	
	private String role;
	
	private String description;
	
	private Boolean available = false;
	
	//用户 - 角色关系，多对多
	@ManyToMany(fetch=FetchType.EAGER) 
	@JoinTable(name="SysUserRole",joinColumns= {@JoinColumn(name="roleId")},inverseJoinColumns= {@JoinColumn(name="uid")})
	private List<UserInfo> userInfos;
	
	//角色 - 权限关系，多对多
	@ManyToMany
	@JoinTable(name="SysRolePermission",joinColumns= {@JoinColumn(name="roleId")},inverseJoinColumns= {@JoinColumn(name="permissionId")})
	private List<SysPermission> permissions;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Boolean getAvailable() {
		return available;
	}


	public void setAvailable(Boolean available) {
		this.available = available;
	}


	public List<UserInfo> getUserInfos() {
		return userInfos;
	}


	public void setUserInfos(List<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}


	public List<SysPermission> getPermissions() {
		return permissions;
	}


	public void setPermissions(List<SysPermission> permissions) {
		this.permissions = permissions;
	}

}
