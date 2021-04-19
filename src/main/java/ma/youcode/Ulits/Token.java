package ma.youcode.Ulits;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ma.youcode.Models.Users;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Token {

    public Map<String, String> generateurJWTTokern(Users user) {
        long time = System.currentTimeMillis();

        String token = Jwts.builder()
                .setExpiration(new Date(time + 2 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, "onlineshopapi")
                .setIssuedAt(new Date(time))
                .claim("Id", user.getId())
                .claim("email", user.getEmail())
                .claim("fullName", user.getFullName())
                .claim("type", user.getType())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
