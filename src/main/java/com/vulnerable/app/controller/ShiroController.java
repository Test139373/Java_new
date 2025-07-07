package com.vulnerable.app.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shiro")
public class ShiroController {

    @GetMapping("/login")
    public String login(@RequestParam String username, 
                      @RequestParam String password) {
        // VULNERABLE: CVE-2016-4437 - Shiro Padding Oracle
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        SimpleAccountRealm realm = new SimpleAccountRealm();
        realm.addAccount("admin", "secret");
        securityManager.setRealm(realm);
        SecurityUtils.setSecurityManager(securityManager);
        
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject currentUser = SecurityUtils.getSubject();
        
        try {
            currentUser.login(token);
            return "Login successful";
        } catch (AuthenticationException e) {
            return "Login failed: " + e.getMessage();
        }
    }
}