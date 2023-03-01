package com.deloitte.myproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.deloitte.myproject.model.User;
import com.deloitte.myproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is a controller. Controller is a component that handles incoming HTTP requests from clients and sends back HTTP responses.
 * Controllers typically contain methods annotated with annotations such as @RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, etc.,
 * which map specific URL patterns to these methods.
 *
 */
@Slf4j
@RestController
@CrossOrigin
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * This method is for getting the list of all resources from DB.
     * @return The list of resources only if exists in DB.
     */
    @Operation(summary = "Get the list of user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No Users found",
                    content = @Content) })
    @GetMapping("/user")
    public List<User> getUser() {
        return userService.getUser();
    }

    /**
     * This method is to get the specific resource by its id
     * @param id The resource id
     * @return The resource found with provided id
     */
    @Operation(summary = "Get the user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * This method is to add a resource to the existing list of resources
     * @param user The request body for a resource to be saved successfully
     * @return The saved resource
     */
    @Operation(summary = "Save the user with the provided request body content")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user is saved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content) })
    @PostMapping("/user")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    /**
     * This method is to update a specific resource if it exists in DB
     * @param id The id of the resource to be updated
     * @param user The request body which has the update
     * @return The updated resource
     */
    @Operation(summary = "Update the user with the provided request body content")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user is updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content) })
    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    /**
     * This method is for deleting the resource with its id
     * @param id The id of the resource to be deleted
     */
    @Operation(summary = "Delete the user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user is deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
