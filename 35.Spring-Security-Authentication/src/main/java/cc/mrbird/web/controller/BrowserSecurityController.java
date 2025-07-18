package cc.mrbird.web.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author MrBird
 */
@RestController
public class BrowserSecurityController {

    private final RequestCache requestCache = new HttpSessionRequestCache();

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @GetMapping("/authentication/require")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        SavedRequest savedRequest = requestCache.getRequest(request, response);
//        if (savedRequest != null) {
//            String targetUrl = savedRequest.getRedirectUrl();
//            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html"))
//                redirectStrategy.sendRedirect(request, response, "/login.html");
//        }

        // 判断是否是浏览器请求 HTML 页面
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("text/html")) {
            redirectStrategy.sendRedirect(request, response, "/login.html");
            return null; // 避免继续输出文本
        }
        return "访问的资源需要身份认证！";
    }
}
