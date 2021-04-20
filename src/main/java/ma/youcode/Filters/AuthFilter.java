package ma.youcode.Filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import ma.youcode.Ulits.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//@Component
public class AuthFilter extends GenericFilterBean {


//    @Autowired
//    private Token token;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //TODO move this method to utils package
        //get the token from http request
        String token = request.getHeader("auth");
        if (token != null) {
            //split the String into two part [Bearer string, the real token]
            String[] bearer = token.split("Bearer");
            //check if the token provided
            if (bearer.length > 1 && bearer[1] != null) {
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
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "The token must be Bearer [token]");
                return;
            }
        } else {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Must provide a token");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
