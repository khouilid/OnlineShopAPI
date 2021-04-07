package ma.youcode.Controllers;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ma.youcode.Exceptions.AuthException;
import ma.youcode.Models.Users;
import ma.youcode.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> saveUser(@RequestBody Map<String, String> user) {
        Map<String, String> msg = new HashMap<>();
        try {
            Users usr = userService.createAccountService(user.get("fullName"), user.get("email"), user.get("pwd"), user.get("type"));
            return new ResponseEntity<>(generateurJWTTokern(usr), HttpStatus.OK);
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
            return new ResponseEntity<>(generateurJWTTokern(usr), HttpStatus.OK);

        } catch (AuthException e) {
            msg.put("message", e.getMessage());
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("users/all/{role}")
    public ResponseEntity<? extends Object> getAll(@PathVariable(name = "role") String role) {
        Map<String, String> msg = new HashMap<>();
        try {
            List<Users> users = userService.getAllUsers(role);
            //return the token
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            msg.put("message", e.getMessage());
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

    }


    private Map<String, String> generateurJWTTokern(Users user) {
        long time = System.currentTimeMillis();

        String token = Jwts.builder()
                .setExpiration(new Date(time + 2 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, "onlineshopapi")
                .setIssuedAt(new Date(time))
                .claim("Id", user.getId())
                .claim("email", user.getEmail())
                .claim("fullName", user.getFullName())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }


}










































