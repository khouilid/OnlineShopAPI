package ma.youcode;


import ma.youcode.Filters.AuthFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/login", "/api/signup")
                .permitAll()
                .anyRequest()
                .authenticated();


        http.antMatcher("/api/user/**").authorizeRequests() //
                .anyRequest().authenticated() //
                .and()
                .addFilterBefore(new AuthFilter(), CsrfFilter.class);

    }


}
