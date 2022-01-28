package com.tweetapp.application.service.usermanager;

import com.tweetapp.application.constants.UserStatus;
import com.tweetapp.application.dto.UserDetail;
import com.tweetapp.application.entity.User;

import java.util.List;

public interface UserService {

    /**
     * Inserts user data in storage
     * @param userDetail
     */
    void registerUser(UserDetail userDetail);

    /**
     * Authenticates user for login
     * @param userDetail
     * @return
     */
    boolean authenticateUser(UserDetail userDetail);

    /**
     * checks if user exists
     * @param userName
     * @return
     */
    boolean existsUser(String userName);

    /**
     * updates user status as provided
     * @param userName
     * @param userStatus
     */
    void updateUserStatus(String userName, UserStatus userStatus);

    /**
     * finds and returns Logged in user entity
     * @return
     */
    User getLoggedInUser();

    /**
     * gets all users
     * @return
     */
    List<UserDetail> getAllUsers();

    /**
     * resets password
     * @param userDetail
     * @param newPassword
     */
    void resetPassword(UserDetail userDetail, String newPassword);


}
