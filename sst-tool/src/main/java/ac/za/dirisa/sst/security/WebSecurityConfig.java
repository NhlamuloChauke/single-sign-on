package ac.za.dirisa.sst.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import ac.za.dirisa.sst.model.enums.UserRole;
import ac.za.dirisa.sst.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    
	@Autowired
	AuthenticationSuccessHandler successHandler;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
            .authorizeRequests()
                .antMatchers("/api/v*/public/registration/**",
                		     "/home/**", 
                		     "/subscribe/**", 
                		     "/api/institution/**",
                             "/api/service/**",
                		     "/js/**", 
                		     "/styles/**", 
                		     "/images/**",
                		     "/assets/**")
                .permitAll()
                .antMatchers("/subscribe/portal").hasAnyRole(UserRole.USER.toString())
                .antMatchers("/subscribe/adminPortal").hasAnyRole(UserRole.ADMIN.toString())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                  .loginPage("/subscribe/login")
     		      .successHandler(successHandler)
     		      .usernameParameter("email")
     		      .passwordParameter("password").permitAll()
     		    .and()
     		    .httpBasic().and()
     		    .rememberMe()
     		      .key("rem-me-key")
     		      .userDetailsService(userService)
     		      .rememberMeParameter("remember") //the name of check box at login page
     		      .rememberMeCookieName("rememberlogin") //the name of the cookie
     		      .tokenValiditySeconds(100) //remember for number of seconds
     		    .and()
     		    .logout()
     		      .invalidateHttpSession(true)
     		      .clearAuthentication(true)
     		      .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
     		      .logoutSuccessUrl("/subscribe/login?logout")
     		   .permitAll();
    }
}