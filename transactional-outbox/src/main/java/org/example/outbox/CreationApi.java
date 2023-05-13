package org.example.outbox;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CreationApi {
    @Autowired
    CreationService creationService;

    @PostMapping("/{studentName}/{password}")
    public UserEntity createUser(@PathVariable("password") String password, @PathVariable("studentName") String name) {
        return creationService.newUser(name, password);
    }
}
