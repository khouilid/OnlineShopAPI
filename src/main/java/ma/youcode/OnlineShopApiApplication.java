package ma.youcode;

import ma.youcode.Filters.AuthFilter;
import ma.youcode.Filters.RoleFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineShopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopApiApplication.class, args);
    }




}
