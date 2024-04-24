package jpa.study;

import jpa.study.common.auth.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, mfk";
    }

    @GetMapping("/hello-auth")
    public String helloAuth(@Auth Long userId) {
        log.info("userId = " + userId);
        return "Hello, Auth: " + userId;
    }
}
