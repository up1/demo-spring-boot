package com.demo.feature01;

import com.demo.JDBCApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JDBCApplication.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        userRepository.delete(Long.valueOf(1));
        userRepository.save(new User(Long.valueOf(1), "Dummy Firstname", "Dummy Lastname"));
    }

    @Test
    public void find_user_by_id_1() {
        User user = userRepository.findById(Long.valueOf(1));
        assertEquals(Long.valueOf(1), user.getId());
        assertEquals("Dummy Firstname", user.getFirstname());
        assertEquals("Dummy Lastname", user.getLastname());
    }

    @Test(expected = UserNotFoundException.class)
    public void not_found_user_2() {
        User user = userRepository.findById(Long.valueOf(2));
    }

}