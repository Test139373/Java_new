package com.vulnerable.app.controller;

import org.apache.log4j.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log4j")
public class Log4jController {

    @GetMapping("/socket")
    public String startSocketServer() {
        // VULNERABLE: CVE-2019-17571 - Log4j SocketServer RCE
        org.apache.log4j.net.SocketServer.main(new String[] {"4712"});
        return "Log4j SocketServer started on port 4712";
    }

    @GetMapping("/jms")
    public String jmsAppender(@RequestParam String jndiUrl) {
        // VULNERABLE: CVE-2021-4104 - JMSAppender JNDI Injection
        Logger logger = Logger.getLogger(Log4jController.class);
        JMSAppender appender = new JMSAppender();
        appender.setTopicConnectionFactoryBindingName(jndiUrl);
        logger.addAppender(appender);
        return "JMSAppender configured with JNDI URL: " + jndiUrl;
    }
}