package com.deloitte.myproject.service.serviceImpl;

import com.deloitte.myproject.model.User;
import com.deloitte.myproject.repository.UserRepository;
import com.deloitte.myproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import com.deloitte.myproject.exception.ResourceNotFoundException;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * This is a service class which is a component that contains the business logic of the application.
 * It typically acts as a middle layer between the controller and the repository, handling the processing of data and applying the necessary business rules before passing the data to the repository for storage or retrieval.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;

    /**
     * The RestTemplate is a class provided by Spring Framework that simplifies communication with HTTP servers and provides convenient methods to perform HTTP requests.
     * Usage tip: MyObject myObject = restTemplate.getForObject(url, MyObject.class);
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * This method handles business logic to save resource into DB
     * @param user The resource to be saved
     * @return The added resource
     */
    @Override
    @CachePut(cacheNames = "User", key = "#user.userId")
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * This method handles business logic to update the resource found in DB by its id
     * @param id The id of the resource
     * @param user The new resource body
     * @return The updated resource
     */
    @Override
    @CachePut(cacheNames = "User", key = "#id")
    public User updateUser(Long id, User user) {
        User userObj = userRepository.findById(id)
                                       .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        userObj.setUserName(user.getUserName());
        return userRepository.save(userObj);
    }

    /**
     * This method handles business logic to delete the resource found in DB by its id
     * @param id The id of the resource
     */
    @Override
    @CacheEvict(cacheNames = "User", key = "#id")
    public void deleteUserById(Long id) {
        userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        userRepository.deleteById(id);
    }

    /**
     * This method handles business logic to retrieve all the resources found in DB
     * @return The resources list
     */
    @Override
    public List<User>getUser() {
        return userRepository.findAll();
    }

    /**
     * This method handles business logic to get a specific resource found in DB by its id
     * @param id The id of the resource
     * @return The found resource
     */
    @Override
    @Cacheable(cacheNames = "User", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }
}