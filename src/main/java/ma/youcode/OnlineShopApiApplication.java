package ma.youcode;

import ma.youcode.Fillters.AuthFilter;
import ma.youcode.Fillters.RoleFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineShopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopApiApplication.class, args);
    }


    @Bean
    public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
        FilterRegistrationBean<AuthFilter> filterRegistration = new FilterRegistrationBean<>();
        AuthFilter authFilter = new AuthFilter();
        filterRegistration.setFilter(authFilter);
        filterRegistration.addUrlPatterns("/api/user/*");
        return filterRegistration;
    }
    @Bean
    public FilterRegistrationBean<RoleFilter> filterRoleBean() {
        FilterRegistrationBean<RoleFilter> filterRegistration = new FilterRegistrationBean<>();
        RoleFilter roleFilter = new RoleFilter();
        filterRegistration.setFilter(roleFilter);
        filterRegistration.addUrlPatterns("/api/user/users/*");
        return filterRegistration;
    }














}
