package cc.mrbird.security.browser;

import cc.mrbird.handler.MyAuthenticationFailureHandler;
import cc.mrbird.handler.MyAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BrowserSecurityConfig  {

    private final MyAuthenticationSuccessHandler authenticationSuccessHandler;

    private final MyAuthenticationFailureHandler authenticationFailureHandler;

    public BrowserSecurityConfig(MyAuthenticationSuccessHandler authenticationSuccessHandler, MyAuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(form -> form
                        .loginPage("/authentication/require") // 登录跳转 URL
                        .loginProcessingUrl("/login") // 处理表单登录 URL
                        .successHandler(authenticationSuccessHandler) // 登录成功处理器
                        .failureHandler(authenticationFailureHandler) // 登录失败处理器
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/authentication/require", "/login.html").permitAll() // 免认证资源
                        .anyRequest().authenticated() // 所有其他请求都需要认证
                )
                .csrf(csrf -> csrf.disable()); // 禁用 CSRF（根据需要启用）

        return http.build();
    }
}
