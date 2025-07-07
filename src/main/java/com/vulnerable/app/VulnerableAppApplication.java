package com.vulnerable.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.log4j.Logger;

@SpringBootApplication
public class VulnerableAppApplication {
    
    // VULNERABLE: CVE-2021-44228 - Log4j JNDI lookup vulnerability (if using log4j2)
    private static final Logger logger = Logger.getLogger(VulnerableAppApplication.class);

    public static void main(String[] args) {
        // VULNERABLE: Log4j 1.x RCE potential
        logger.info("Starting Vulnerable Application with args: " + args);
        
        SpringApplication.run(VulnerableAppApplication.class, args);
    }
}