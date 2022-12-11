package com.bt_akademi.user_management.controller;

import com.bt_akademi.user_management.model.entity.User;
import com.bt_akademi.user_management.model.service.AbstractAuthenticationService;
import com.bt_akademi.user_management.model.service.AbstractUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("api/authentication")
@RestController
public class AuthenticationController {

    @Autowired
    private AbstractUserService userService; // kullanıcı kaydı

    @Autowired
    private AbstractAuthenticationService authenticationService; // oturum açarken

    // **** 12 -> ProductController
       @PostMapping("sign-in")
       public ResponseEntity signIn(@RequestBody User user){

           String signInJWT = authenticationService.GenerateJWT(user);

           return new  ResponseEntity(signInJWT,HttpStatus.OK);
       }

       @PostMapping("sign-up")
       public ResponseEntity signUp(@RequestBody User user) {

           if(userService.findByUsername(user.getUsername()).isPresent()) {
               return new ResponseEntity<>(HttpStatus.CONFLICT);
           }
           return new ResponseEntity(userService.save(user),HttpStatus.CREATED);
       }
}
