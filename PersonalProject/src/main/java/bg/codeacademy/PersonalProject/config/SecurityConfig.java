package bg.codeacademy.PersonalProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

  @Override
  protected void configure(HttpSecurity http) throws Exception
  {
    http
        .authorizeRequests()
        // allow users to register
        .antMatchers(HttpMethod.POST, "/project/user/add").permitAll()
        //allow only Admins to do the following
        .antMatchers(HttpMethod.POST, "/project/tasks/add").hasRole("Admins")
        .antMatchers(HttpMethod.POST, "/project/group/add").hasRole("Admins")
        .antMatchers(HttpMethod.PUT, "/project/group/change").hasRole("Admins")
        // all other API`s are authenticated for all users
        .antMatchers("/project/**").authenticated()
        .and()
        .httpBasic();

    http.csrf().disable();
    http.headers().frameOptions().disable();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder()
  {
    return new BCryptPasswordEncoder();
  }
}
