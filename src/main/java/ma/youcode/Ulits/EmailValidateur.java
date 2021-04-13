package ma.youcode.Ulits;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidateur {


    public boolean isValid(String email) {
        //check the regex of provided email
        Pattern regexOfEmail = Pattern.compile("^(.+)@(.+)$");
        if (email != null) email = email.toLowerCase();
        return regexOfEmail.matcher(email).matches();
    }

}
