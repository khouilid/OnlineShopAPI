package ma.youcode.Services;


import ma.youcode.Exceptions.AuthException;
import ma.youcode.Models.OurUserDetails;
import ma.youcode.Models.Users;
import ma.youcode.Repositorys.UserRepository;
import ma.youcode.Ulits.EmailValidateur;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface, UserDetailsService {


    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailValidateur emailValidateur;


    public Optional<Users> getByID(Long id) {
        Optional<Users> user = userRepository.findById(id);
        if (user.isEmpty()) throw new AuthException("No user found");
        return user;
    }


    public Users loginService(String email, String pwd) {
        if (!emailValidateur.isValid(email)) throw new AuthException("Invalid email");
        Users user = userRepository.findByEmail(email);
        if (user == null || !BCrypt.checkpw(pwd, user.getPwd())) throw new AuthException("Invalid email or password!");
        if (user.getStatus()) throw new AuthException("Your Account has been blocked or removed");
        return user;
    }


    public Users createAccountService(String fullName, String email, String pwd, String type) throws AuthException {

        if (!emailValidateur.isValid(email)) throw new AuthException("Email not valid");
        //check if email on use
        Long usersCount = userRepository.countByEmail(email);
        if (usersCount > 0) throw new AuthException("Email already used");
        //check the size of pwd chars
        if (pwd.length() < 6) throw new AuthException("Password so short");

        return userRepository.save(new Users(fullName, email, BCrypt.hashpw(pwd, BCrypt.gensalt(12)), type));


    }


    public List<Users> getAllUsers(String role) {

        List<Users> users = userRepository.findByType(role);
        if (users.size() == 0) throw new AuthException("No users founds");
        return users;

    }


    public void changeUserAccountStatus(Long id, Boolean stt) {
        try {
            //check account exist
            Optional<Users> victime = userRepository.findById(id);
            if (victime.isEmpty()) throw new AuthException("Account not exist");
            Users user = victime.get();
            //update it status = block it
            user.setStatus(stt);
            userRepository.save(user);
        } catch (AuthException e) {
            throw new AuthException("Invalid request");
        }
    }


    public void updateAccoutInfos(Users users) {

        if (!emailValidateur.isValid(users.getEmail())) throw new AuthException("Email not valid");
        //check if email on use
        Long usersCount = userRepository.countByEmail(users.getEmail());
        if (usersCount > 0) throw new AuthException("Email already used");
        if (users.getPwd().length() < 6) throw new AuthException("Password so short");
        userRepository.save(users);
    }

    public Users confirmEmail(Long user_id){
        Optional<Users> user = userRepository.findById(user_id);
        if (!user.isEmpty()){

        user.get().setEmail_validation(true);
        }
        return userRepository.save(user.get());
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new OurUserDetails(user);
    }
}
