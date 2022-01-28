package com.tweetapp.application.service.usermanager;

import com.tweetapp.application.constants.ErrorMessage;
import com.tweetapp.application.constants.UserStatus;
import com.tweetapp.application.dto.UserDetail;
import com.tweetapp.application.entity.User;
import com.tweetapp.application.exception.ApplicationException;
import com.tweetapp.application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Inserts user data in storage
     *
     * @param userDetail
     */
    @Override
    public void registerUser(UserDetail userDetail) {
        if(userRepository.existsByUserName(userDetail.getUserName())) {
            throw new ApplicationException(ErrorMessage.DUPLICATE_EMAIL_INPUT.getDescription());
        }
        User user = new User(userDetail.getUserName(), userDetail.getPassword());
        userRepository.save(user);
    }

    /**
     * Authenticates user for login
     *
     * @param userDetail
     * @return
     */
    @Override
    public boolean authenticateUser(UserDetail userDetail) {
        return userRepository.existsByUserNameAndPassword(userDetail.getUserName(), userDetail.getPassword());
    }

    /**
     * checks if user exists
     *
     * @param userName
     * @return
     */
    @Override
    public boolean existsUser(String userName) {
        return userRepository.existsByUserName(userName);
    }

    /**
     * updates user status as provided
     *
     * @param userName
     * @param userStatus
     */
    @Override
    public void updateUserStatus(String userName, UserStatus userStatus) {
        User user = userRepository.findByUserName(userName);
        user.setUserStatus(userStatus);
        user.setLastUpdatedOn(new Date());
        userRepository.save(user);
    }

    /**
     * finds and returns Logged in user entity
     *
     * @return
     */
    @Override
    public User getLoggedInUser() {
        return userRepository.findByUserStatus(UserStatus.LOGGED_IN);
    }

    /**
     * gets all users
     *
     * @return
     */
    @Override
    public List<UserDetail> getAllUsers() {
        return convertUserToUserDetails(userRepository.findAll());
    }

    /**
     * resets password
     *
     * @param userDetail
     * @param newPassword
     */
    @Override
    public void resetPassword(UserDetail userDetail, String newPassword) {
        User user = userRepository.findByUserName(userDetail.getUserName());
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    private List<UserDetail> convertUserToUserDetails(Iterable<User> users) {
        List<UserDetail> userDetails = new ArrayList<UserDetail>();
        users.forEach(user -> {
            UserDetail userDetail = new UserDetail();
            userDetail.setUserName(user.getUserName());
            userDetail.setCreatedOn(user.getCreatedOn());
            userDetails.add(userDetail);
        });
        return userDetails;
    }
}
