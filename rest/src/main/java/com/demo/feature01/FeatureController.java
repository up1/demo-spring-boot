package com.demo.feature01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("feature01")
public class FeatureController {

    private final UserRepository userRepository;

    @Autowired
    public FeatureController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/flow01/{userId}", method = GET)
    public User firstFlow(@PathVariable Long userId) {
        return this.userRepository.findById(userId);
    }

}
