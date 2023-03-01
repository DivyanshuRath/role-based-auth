package com.deloitte.myproject.util;

import com.deloitte.myproject.model.User;

public class UserTestUtil {
    public static User createUser(Long id, String name) {

        User user = new User();
        user.setUserId(id);
        user.setUserName(name);

        return user;
    }
}
