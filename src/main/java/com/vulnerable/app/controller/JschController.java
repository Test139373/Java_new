package com.vulnerable.app.controller;

import com.jcraft.jsch.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jsch")
public class JschController {

    @GetMapping("/connect")
    public String connect(@RequestParam String host) throws JSchException {
        // VULNERABLE: CVE-2016-5725 - JSch Host Key Verification Bypass
        JSch jsch = new JSch();
        Session session = jsch.getSession("user", host, 22);
        session.setConfig("StrictHostKeyChecking", "no"); // Disables verification
        session.connect();
        return "Connected to " + host + " without host key verification";
    }
}