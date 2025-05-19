package cc.mrbird.security.browser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 浏览器安全配置
 * */
@Configuration
public class BrowserSecurityConfig {
    /**
     * 安全控制链
     * */
    @Bean
    public SecurityFilterChain browserSecurityFilterChain(HttpSecurity http) throws Exception {
        /*
        * 1.http basic认证
        * */
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults());

        /*
        * 2.表单认证
        * */
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                // 允许所有人访问登录页
                .formLogin(
                        form -> form
//                        .loginPage("/login")            // 自定义登录页路径
                        .permitAll()                    // 登录页允许匿名访问
//                        .defaultSuccessUrl("/home")     // 登录成功后跳转
//                        .failureUrl("/login?error")     // 登录失败跳转
                );
//        FilterSecurityInterceptor
//        UsernamePasswordAuthenticationFilter
        return http.build();
    }
}
