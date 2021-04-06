package ma.youcode.Services;


import ma.youcode.Exceptions.AuthException;
import ma.youcode.Models.Users;
import ma.youcode.Repositorys.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {


    @Autowired
    UserRepository userRepository;


    private boolean isValid(String email) {
        //check the regex of provided email
        Pattern regexOfEmail = Pattern.compile("^(.+)@(.+)$");
        if (email != null) email = email.toLowerCase();
        return !regexOfEmail.matcher(email).matches();
    }


    public Users loginService(String email, String pwd) {
        if (isValid(email)) throw new AuthException("Email not valid");

        Users user = userRepository.findByEmail(email);
        if (user == null) throw new AuthException("Wrong email");
        if (BCrypt.checkpw(pwd, user.getPwd())) throw new AuthException("Wrong password");
        return user;

    }


    public Users createAccountService(String fullName, String email, String pwd, String type) throws AuthException {

        if (isValid(email)) throw new AuthException("Email not valid");
        //check if email on use
        Long usersCount = userRepository.countByEmail(email);
        if (usersCount > 0) throw new AuthException("Email already used");
        //check the size of pwd chars
        if (pwd.length() < 6) throw new AuthException("Password so short");

        return userRepository.save(new Users(fullName, email, BCrypt.hashpw(pwd, BCrypt.gensalt()), type));


    }


}
