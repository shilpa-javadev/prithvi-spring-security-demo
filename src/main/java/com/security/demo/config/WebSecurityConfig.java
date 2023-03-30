package com.security.demo.config;

import com.security.demo.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
*   Here we create a set of beans - UserDetailsService, Password encoder,DaoAuthenticationProvider
*   Basically DaoAuthenticationProvider needs UserDetailsService and PasswordEncoder objects.
* */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public WebSecurityConfig(UserService userService, BCryptPasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    //configure method's paramter AuthenticationManagerBuilder needs the above authenticationProvider
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    //the below method completely takes care of everything right from authentication till authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/api/v1/posts/remove-post/{postId}").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.PUT, "/api/v1/posts/edit-post/{postId}").hasAuthority("USER")
//                .antMatchers("/api/v1/posts/add-post").hasAuthority("USER")
//                .antMatchers("/api/v1/posts/read-post/{postId}").hasAnyAuthority("USER","GUEST","ADMIN")
                .antMatchers("/profiles/api/v1/login","/profiles/api/v1/register").permitAll()
//                .antMatchers("/api/v1/posts/read-posts").permitAll()
                .antMatchers("**/posts/**","/profiles/**").hasAuthority("USER")
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .permitAll()
                .and()
                .httpBasic();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}

    /*//the below method completely takes care of everything right from authentication till authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/api/v1/posts/remove-post/{postId}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/posts/edit-post/{postId}").hasAuthority("USER")
                .antMatchers("/api/v1/posts/add-post").hasAuthority("USER")
                .antMatchers("/api/v1/posts/read-post/{postId}").hasAnyAuthority("USER","GUEST","ADMIN")
                .antMatchers("/profiles/api/v1/login","/profiles/api/v1/register").permitAll()
                .antMatchers("/api/v1/posts/read-posts").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .permitAll()
                .and()
                .httpBasic();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}*/

/* .csrf().disable()
         .authorizeRequests()
         .antMatchers(HttpMethod.DELETE, "/api/v1/products/{productId}").hasRole(ADMIN.name()) // Admin should be able to delete
         .antMatchers(HttpMethod.PUT, "/api/v1/products/{productId}").hasRole(ADMIN.name()) // Admin should be able to update
         .antMatchers("/api/v1/products/add").hasAnyRole(ADMIN.name(), SUPERVISOR.name()) // Admin and Supervisor should be able to add product.
         .antMatchers("/api/v1/products").hasAnyRole(ADMIN.name(), SUPERVISOR.name(), INTERN.name()) // All three users should be able to get all products.
         .antMatchers("/api/v1/products{productId}").hasAnyRole(ADMIN.name(), SUPERVISOR.name(), INTERN.name()) // All three users should be able to get a product by id.
         .anyRequest()
         .authenticated()
         .and()
         .httpBasic();*/
