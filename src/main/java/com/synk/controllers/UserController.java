package com.synk.controllers;

import com.synk.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ExampleController
 *
 * @author danielpadua
 *
 */
@RestController
@RequestMapping("/api/example")
public class UserController {

    @GetMapping("/hello-world")
    public ResponseEntity<User> get() {
        return ResponseEntity.ok(new User());
    }

}
