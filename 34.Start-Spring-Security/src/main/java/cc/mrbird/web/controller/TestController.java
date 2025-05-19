package cc.mrbird.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("hello")
    public String hello() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return "hello spring security";
    }
}
