package com.bakigoal.springdatajpademo.service;

import com.bakigoal.springdatajpademo.model.User;

public interface UserService {

    User getUser(Integer userId);

    User updatePerson(User user);

    void deleteUserById(Integer userId);

    void evictCache();
}
