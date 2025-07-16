package cc.mrbird.security.browser;

import cc.mrbird.handler.MyAuthenticationFailureHandler;
import cc.mrbird.handler.MyAuthenticationSucessHandler;
import cc.mrbird.validate.code.ValidateCodeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class BrowserSecurityConfig  {

    private final MyAuthenticationSucessHandler authenticationSuccessHandler;

    private final MyAuthenticationFailureHandler authenticationFailureHandler;

    private final ValidateCodeFilter validateCodeFilter;

    public BrowserSecurityConfig(MyAuthenticationSucessHandler authenticationSuccessHandler, MyAuthenticationFailureHandler authenticationFailureHandler, ValidateCodeFilter validateCodeFilter) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.validateCodeFilter = validateCodeFilter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class) // 添加验证码校验过滤器
//                .formLogin() // 表单登录
//                // http.httpBasic() // HTTP Basic
//                .loginPage("/authentication/require") // 登录跳转 URL
//                .loginProcessingUrl("/login") // 处理表单登录 URL
//                .successHandler(authenticationSucessHandler) // 处理登录成功
//                .failureHandler(authenticationFailureHandler) // 处理登录失败
//                .and()
//                .authorizeRequests() // 授权配置
//                .antMatchers("/authentication/require",
//                        "/login.html",
//                        "/code/image").permitAll() // 无需认证的请求路径
//                .anyRequest()  // 所有请求
//                .authenticated() // 都需要认证
//                .and().csrf().disable();
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form
                        .loginPage("/authentication/require")
                        .loginProcessingUrl("/login")
                        .successHandler(authenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/authentication/require", "/login.html", "/code/image").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
