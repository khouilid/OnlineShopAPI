package ma.youcode.Controllers;


import ma.youcode.Exceptions.AuthException;
import ma.youcode.Models.Users;
import ma.youcode.Services.UserServiceInterface;
import ma.youcode.Ulits.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class UserController {


    @Autowired
    private UserServiceInterface userService;
    @Autowired
    private Token token;


    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> saveUser(@RequestBody Map<String, String> user) {
        Map<String, String> msg = new HashMap<>();
        try {
            Users usr = userService.createAccountService(user.get("fullName"), user.get("email"), user.get("pwd"), user.get("type"));
            usr.setStatus(false);
            return new ResponseEntity<>(token.generateurJWTTokern(usr), HttpStatus.CREATED);
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


    @GetMapping("/user/users/all/{role}")
    public ResponseEntity<? extends Object> getAllUsers(@PathVariable(name = "role") String role) {

        try {
            List<Users> users = userService.getAllUsers(role);
            //return the token
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> msg = new HashMap<>();
            msg.put("message", e.getMessage());
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/users/find/{id}")
    public ResponseEntity<? extends Object> findUserByID(@PathVariable(name = "id") Long id) {
        try {
            Optional<Users> user = userService.getByID(id);

            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (Exception e) {
            Map<String, String> msg = new HashMap<>();
            msg.put("message", e.getMessage());
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/user/users/delete/{id}")
    public ResponseEntity<? extends Object> blockUserAccount(@PathVariable("id") Long id) {
        Map<String, Object> msg = new HashMap<>();
        try {
            userService.removeUserAccount(id);
            msg.put("success", true);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            msg.put("success", false);
            msg.put("message", e.getMessage());
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/user/update")
    public ResponseEntity<? extends Object> updateAccount(HttpServletRequest request, @RequestBody Map<String, String> user) {
        Map<String, Object> msg = new HashMap<>();
        Long id = (Long) request.getAttribute("id");
        try {
            Users usr = new Users(id, user.get("fullName"), user.get("email"), user.get("pwd"), user.get("type"), false);
            userService.updateAccoutInfos(usr);
            msg.put("success", true);
            return new ResponseEntity<>(msg, HttpStatus.OK);

        } catch (Exception e) {
            msg.put("success", false);
            msg.put("message", e.getMessage());
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);

        }

    }


}