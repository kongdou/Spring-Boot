package fun.deepsky.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fun.deepsky.jwt.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
