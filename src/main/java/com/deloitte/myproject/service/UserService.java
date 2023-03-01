package com.deloitte.myproject.service;

import com.deloitte.myproject.model.User;
import java.util.List;

/**
 * This is the service layer interface which declares all methods that implementation class should implement
 */
public interface UserService {

    /**
     * Save resource method declaration
     */
    User saveUser(User user);

    /**
     * Update resource method declaration
     */
    User updateUser(Long id, User user);

    /**
     * Delete resource method declaration
     */
    void deleteUserById(Long id);

    /**
     * Get all resources method declaration
     */
    List<User> getUser();

    /**
     * Get a specific resource method declaration
     */
    User getUserById(Long id);

}