package ma.youcode;

import ma.youcode.Fillters.AuthFilter;
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
        filterRegistration.addUrlPatterns("/api/user/users/*");
        return filterRegistration;
    }
}
