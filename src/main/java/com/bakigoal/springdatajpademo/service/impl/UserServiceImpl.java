package com.bakigoal.springdatajpademo.service.impl;

import com.bakigoal.springdatajpademo.model.User;
import com.bakigoal.springdatajpademo.repository.UserRepository;
import com.bakigoal.springdatajpademo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Put a person into a cache named as ‘users’, identifies that person by the key as ‘userId’
     * and will only store a user with followers greater than 10000.
     * This makes sure that cache is populated with users who are very popular and are often queried for.
     */
    @Override
    @Cacheable(value = "users", key = "#userId", unless = "#result.followers < 10000")
    public User getUser(Integer userId) {
        log.info("UserService.getUser: calling user repository...");
        return userRepository.findById(userId).orElseThrow(RuntimeException::new);
    }

    /**
     * Cache values should also update whenever their actual objects value are updated
     */
    @Override
    @CachePut(value = "users", key = "#user.id")
    public User updatePerson(User user) {
        log.info("update user: {}", user);
        userRepository.save(user);
        return user;
    }

    @Override
    @CacheEvict(value = "users", key = "#userId")
    public void deleteUserById(Integer userId) {
        log.info("deleting person with id {}", userId);
        userRepository.deleteById(userId);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void evictCache() {
    }
}
