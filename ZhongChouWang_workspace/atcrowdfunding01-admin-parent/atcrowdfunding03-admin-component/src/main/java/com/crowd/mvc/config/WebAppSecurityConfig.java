package com.crowd.mvc.config;

import com.crowd.util.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

//    @Bean
//    public BCryptPasswordEncoder getPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {

//        builder.inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("tom")
//                .password(new BCryptPasswordEncoder().encode("123123"))
//                .roles("ADMIN");
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);


    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {

        security
                .authorizeRequests() // 对请求进行授权
                .antMatchers("/admin/to/login/page.html")// 针对登录页进行设置
                .permitAll() // 无条件访问
                .antMatchers("/bootstrap/**") // 针对静态资源进行设置，无条件访问
                .permitAll() // 针对静态资源进行设置，无条件访问
                .antMatchers("/crowd/**") // 针对静态资源进行设置，无条件访问
                .permitAll() // 针对静态资源进行设置，无条件访问
                .antMatchers("/css/**") // 针对静态资源进行设置，无条件访问
                .permitAll() // 针对静态资源进行设置，无条件访问
                .antMatchers("/fonts/**") // 针对静态资源进行设置，无条件访问
                .permitAll() // 针对静态资源进行设置，无条件访问
                .antMatchers("/img/**") // 针对静态资源进行设置，无条件访问
                .permitAll() // 针对静态资源进行设置，无条件访问
                .antMatchers("/jquery/**") // 针对静态资源进行设置，无条件访问
                .permitAll()
                .antMatchers("/layer/**") // 针对静态资源进行设置，无条件访问
                .permitAll() // 针对静态资源进行设置，无条件访问
                .antMatchers("/script/**") // 针对静态资源进行设置，无条件访问
                .permitAll() // 针对静态资源进行设置，无条件访问
                .antMatchers("/ztree/**") // 针对静态资源进行设置，无条件访问
                .permitAll()
                .antMatchers("/admin/get/page.html")
                .hasRole("经理")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                        request.setAttribute("exception", new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                        request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request, response);
                    }
                })
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/admin/to/login/page.html")
                .loginProcessingUrl("/security/do/login.html")
                .defaultSuccessUrl("/admin/to/main/page.html")
                .usernameParameter("loginAcct")
                .passwordParameter("userPswd")
                .and()
                .logout()
                .logoutUrl("/seucrity/do/logout.html")
                .logoutSuccessUrl("/admin/to/login/page.html")
                ;

    }
}
