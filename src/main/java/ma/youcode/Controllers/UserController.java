package ma.youcode.Controllers;


import ma.youcode.Exceptions.AuthException;
import ma.youcode.Models.Users;
import ma.youcode.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/save")
    public ResponseEntity<Map<String ,String>> saveUser(@RequestBody Map<String, String> user) {
        Map<String , String> msg = new HashMap<>();
        try {
             userService.createAccountService(user.get("fullName"), user.get("email"), user.get("pwd"), user.get("type"));
             msg.put("message" , "Account created successfully");
             return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            msg.put("message", e.getMessage());

            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("/login")
    public ResponseEntity<? extends Object>  login(@RequestBody Map<String , String> user) throws AuthException{
        Map<String , String> msg = new HashMap<>();
        try {
            Users usr = userService.loginService(user.get("email"), user.get("pwd"));
            usr.setPwd(null);
            return new ResponseEntity<>(usr, HttpStatus.OK);

        } catch (AuthException e) {
            msg.put("message", e.getMessage());
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }









}