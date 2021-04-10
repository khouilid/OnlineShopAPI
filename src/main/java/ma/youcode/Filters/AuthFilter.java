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


@Component
//@Order(1)
public class AuthFilter extends GenericFilterBean {


    @Autowired
    private Token token;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)  servletResponse;

        token.tokenValidateur(request, response);

        filterChain.doFilter(request, response);


    }
}
