package bg.softuni.quizzical.config;

import bg.softuni.quizzical.model.service.UserDTO;
import bg.softuni.quizzical.service.UserService;
import bg.softuni.quizzical.service.impl.UserDetailsServiceImpl;
import bg.softuni.quizzical.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;


    public ApplicationSecurityConfiguration(UserServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }


    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(this.csrfTokenRepository())
                .and()
                .authorizeRequests()
                .antMatchers("/css/**", "/images/**", "/fonts/**", "/js/**", "/scss/**").permitAll()
                .antMatchers("/",
                        "/error",
                        "/about",
                        "/users/register",
                        "/users/login",
                        "/v2/api-docs",
                        "/swagger-ui.html").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/users/login")
                .loginPage("/users/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
                .and()
                .rememberMe()
                .key("devUniSecret")
                .tokenValiditySeconds(604800)
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("rememberMECOOKIE")
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/");

    }
}
