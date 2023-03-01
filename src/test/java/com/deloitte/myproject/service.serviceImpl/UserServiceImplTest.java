package com.deloitte.myproject.service.serviceImpl;

import com.deloitte.myproject.model.User;
import com.deloitte.myproject.repository.UserRepository;
import com.deloitte.myproject.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.deloitte.myproject.util.UserTestUtil.createUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;


    @Test
    void saveUser() {

        User user = createUser(1L,"name");
        Mockito.when(userRepository.save(any(User.class)))
                .thenReturn(user);
        User user1 = userService.saveUser(user);
        Assertions.assertNotNull(user1);
    }

    @Test
    void updateUser() {
        User user = createUser(1L,"name");
        Mockito.when(userRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(any())).thenReturn(user);
        User user1 = userService.updateUser(1L,user);

        Assertions.assertEquals(user.getUserName(),user1.getUserName());
    }

    @Test
    void deleteUserById() {
        User user = createUser(1L,"name");
        Mockito.when(userRepository.findById(Mockito.anyLong()))
        .thenReturn(Optional.of(user));
        Mockito.doNothing()
                .when(userRepository).deleteById(Mockito.any(Long.class));

        userService.deleteUserById(1L);

        verify(userRepository, times(1)).deleteById(1L);

    }

    @Test
    void getUser() {

        List<User> userList = new ArrayList<>();
        User user1 = createUser(1L,"name1");
        User user2 = createUser(2L,"name2");
        userList.add(user1);
        userList.add(user2);

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        List<User> user = userService.getUser();

        Assertions.assertEquals(userList.size(),user.size());

    }

    @Test
    void getUserById() {
        User user = createUser(1L,"name");

        Mockito.when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(user));

        User user1 = userService.getUserById(1L);

        Assertions.assertEquals(user.getUserName(),user1.getUserName());
    }

    @Test
    void updateUserFailure() {
            User user = createUser(1L,"name");
            userService.saveUser(user);
            ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
        () -> userService.updateUser(2L,user));

        assertEquals("User Not Found", resourceNotFoundException.getMessage());
        }

    @Test
    void getUserByIdFailure() {
            ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
        () -> userService.getUserById(anyLong()));

        assertEquals("User Not Found", resourceNotFoundException.getMessage());
        }

    @Test
    void deleteUserByIdFailure() {
            ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class,
        () -> userService.deleteUserById(anyLong()));

        assertEquals("User Not Found", resourceNotFoundException.getMessage());
        }
}