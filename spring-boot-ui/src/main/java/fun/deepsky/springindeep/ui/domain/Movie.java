package fun.deepsky.springindeep.ui.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@JsonIdentityInfo(generator=JSOGGenerator.class)
@NodeEntity
public class Movie {

	@GraphId
	Long id;
	private String name;
	private String photo;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	
	
	@Relationship(type="扮演",direction=Relationship.INCOMING)
	List<Role> roles = new ArrayList<>();
	
	public Role addRole(Actor actor,String name) {
		Role role = new Role(actor,this,name);
		this.roles.add(role);
		return role;
	}
	
	public Movie() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
