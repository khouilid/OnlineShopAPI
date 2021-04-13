package ma.youcode.Services;

import ma.youcode.Exceptions.AuthException;
import ma.youcode.Models.Users;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    Optional<Users> getByID(Long id);


    Users loginService(String email, String pwd);


    Users createAccountService(String fullName, String email, String pwd, String type) throws AuthException;


    List<Users> getAllUsers(String role);


    void removeUserAccount(Long id);


    void updateAccoutInfos(Users users);
}
