package czar.evaluaciones;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import czar.evaluaciones.services.UserService;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    private Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    @Override
    protected void configure(HttpSecurity http) {
        try {
            http
                .authorizeRequests()
                    .antMatchers("/css/**", "/fonts/**", "/img/**", "/js/**", "/favicon.ico").permitAll()   
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
                    .antMatchers("/").hasAnyRole("ADMIN", "MANAGER", "USER")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    .permitAll()
                    .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("remember-me")
                .logoutSuccessUrl("/login?logout")
                    .permitAll()
                    .and()
                    .rememberMe();
        } catch (Exception e) {
            logger.error("WebSecurityConfing.configure", e);
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, UserService userService) {
        try {
            auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
        } catch (Exception e) {
            logger.error("WebSecurityConfig.configureGlobal", e);
        }
    }
}
