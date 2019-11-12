package fun.deepsky.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fun.deepsky.jwt.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByEmail(String email);
}
