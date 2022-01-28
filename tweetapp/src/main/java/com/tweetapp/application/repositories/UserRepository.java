package com.tweetapp.application.repositories;


import com.tweetapp.application.constants.UserStatus;
import com.tweetapp.application.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

    boolean existsByUserNameAndPassword(String userName, String password);

    User findByUserName(String userName);

    User findByUserStatus(UserStatus loggedIn);

    boolean existsByUserName(String userName);
}
