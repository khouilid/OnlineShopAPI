package ma.youcode.Controllers;


import ma.youcode.Models.Users;
import ma.youcode.Services.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


//TODO should config CORS
@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private UserServiceInterface userService;

    @GetMapping("/users/all/{role}")
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

    @GetMapping("/users/find/{id}")
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


    @PutMapping("/users/delete/{id}")
    public ResponseEntity<? extends Object> blockUserAccount(@PathVariable("id") Long id) {
        return changeStatus(id, true);
    }

    @PutMapping("/users/unblock/{id}")
    public ResponseEntity<? extends Object> unblockUserAccount(@PathVariable("id") Long id) {
        return changeStatus(id, false);
    }

    @PutMapping("/update")
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


    private ResponseEntity<? extends Object> changeStatus(Long id, Boolean stt) {
        Map<String, Object> msg = new HashMap<>();
        try {
            userService.changeUserAccountStatus(id, stt);
            msg.put("success", true);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            msg.put("success", false);
            msg.put("message", e.getMessage());
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }


}