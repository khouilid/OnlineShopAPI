package ma.youcode.Ulits;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ma.youcode.Models.Users;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    public void tokenValidateur(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //get the token from http request
        String token = request.getHeader("auth");
        if (token != null){
            //split the String into two part
            //1 - Bearer string
            //2 - the real token
            String[] bearer = token.split("Bearer");
            //check if the token provided
            if(bearer.length > 1 && bearer[1] != null ){
                token = bearer[1];
                try {
                    //parse the String into json
                    Claims claims = Jwts.parser().setSigningKey("onlineshopapi").parseClaimsJws(token).getBody();

                    //sent the user is around the app with http request
                    request.setAttribute("id", Long.parseLong(claims.get("Id").toString()));
                    request.setAttribute("type", claims.get("type").toString());

                } catch (Exception e) {
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
                    return;
                }
            }else {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "The token must be Bearer [token]");
                return;
            }

        }else {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Must provide a token");
            return;
        }

    }





}
