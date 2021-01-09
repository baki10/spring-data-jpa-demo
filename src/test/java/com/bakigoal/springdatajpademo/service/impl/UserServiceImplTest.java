package com.bakigoal.springdatajpademo.service.impl;

import com.bakigoal.springdatajpademo.model.User;
import com.bakigoal.springdatajpademo.repository.UserRepository;
import com.bakigoal.springdatajpademo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userService.evictCache();
    }

    @Test
    public void getFromCacheTest() {
        int cachedId = 1;
        int notCachedId = 2;
        mockUser(cachedId, "John Snow", 10000);
        mockUser(notCachedId, "Night King", 8000);

        // call getUser 5 times
        int count = 5;
        IntStream.range(0, count).forEach(i -> userService.getUser(cachedId));
        IntStream.range(0, count).forEach(i -> userService.getUser(notCachedId));

        verify(userRepository, times(1)).findById(cachedId);
        verify(userRepository, times(count)).findById(notCachedId);
    }

    @Test
    public void updateShouldAlsoUpdateCache() {
        int id = 1;
        mockUser(id, "John Snow", 10000);
        User user = userService.getUser(id);
        int newFollowers = 15000;
        user.setFollowers(newFollowers);
        userService.updatePerson(user);

        User cachedUser = userService.getUser(id);

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(user);
        assertEquals(cachedUser.getFollowers(), newFollowers);
    }

    private void mockUser(int id, String name, int followers) {
        User user = new User(name, followers);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
    }
}