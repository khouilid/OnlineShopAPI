package ma.youcode.Controllers;

import ma.youcode.Exceptions.AuthException;
import ma.youcode.Models.Users;
import ma.youcode.Services.UserServiceInterface;
import ma.youcode.Ulits.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping("/api/")
public class LogginSignupController {
    @Autowired
    private UserServiceInterface userService;
    @Autowired
    private Token token;

    @Autowired
    private PasswordEncoder passwordEncoder;




    @GetMapping("/hello")
    public String helloWorld(){
        return "hello the fucking world";
    }


    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> saveUser(@RequestBody Map<String, String> user) {
        Map<String, String> msg = new HashMap<>();
        try {
            Users usr = userService.createAccountService(user.get("fullName"), user.get("email"), user.get("pwd"), user.get("type"));
            usr.setStatus(false);
            msg.put("message", "Account created successfully, you'll be able to login when the administrator confirm your account");
            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        } catch (Exception e) {
            msg.put("message", e.getMessage());
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("/login")
    public ResponseEntity<? extends Object> login(@RequestBody Map<String, String> user) throws AuthException {
        Map<String, String> msg = new HashMap<>();
        try {
            Users usr = userService.loginService(user.get("email"), user.get("pwd"));
            //return the token
            return new ResponseEntity<>(token.generateurJWTTokern(usr), HttpStatus.OK);
        } catch (AuthException e) {
            msg.put("message", e.getMessage());
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }
}
