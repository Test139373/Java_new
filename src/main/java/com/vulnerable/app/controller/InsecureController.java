package com.vulnerable.app.controller;

import com.vulnerable.app.model.User;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.Base64;

@RestController
@RequestMapping("/insecure")
public class InsecureController {

    // VULNERABLE: Hardcoded credentials (CWE-798)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vulndb";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "password123";

    @GetMapping("/sql")
    public String sqlInjection(@RequestParam String id) {
        // VULNERABLE: SQL Injection (CWE-89)
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = " + id);
            
            StringBuilder result = new StringBuilder();
            while (rs.next()) {
                result.append(rs.getString("username")).append(", ");
            }
            return "Users: " + result.toString();
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/deserialize")
    public String insecureDeserialization(@RequestBody String base64Data) {
        // VULNERABLE: Insecure Deserialization (CWE-502)
        byte[] data = Base64.getDecoder().decode(base64Data);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            Object obj = ois.readObject();
            return "Deserialized object: " + obj.toString();
        } catch (Exception e) {
            return "Deserialization failed: " + e.getMessage();
        }
    }

    @GetMapping("/xss")
    public String xss(@RequestParam String input) {
        // VULNERABLE: Cross-Site Scripting (XSS) (CWE-79)
        return "<html><body><div>" + input + "</div></body></html>";
    }

    @GetMapping("/command")
    public String commandInjection(@RequestParam String cmd) {
        // VULNERABLE: Command Injection (CWE-78)
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            java.util.Scanner s = new java.util.Scanner(p.getInputStream()).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}