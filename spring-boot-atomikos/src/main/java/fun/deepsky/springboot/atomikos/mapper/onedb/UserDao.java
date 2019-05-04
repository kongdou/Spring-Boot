package fun.deepsky.springboot.atomikos.mapper.onedb;

import java.util.List;

import org.springframework.stereotype.Repository;

import fun.deepsky.springboot.atomikos.domain.User;

@Repository
public interface UserDao{

	int insert(User record);
    List<User> selectUsers();

}
