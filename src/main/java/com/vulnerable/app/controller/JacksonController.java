package com.vulnerable.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jackson")
public class JacksonController {

    @PostMapping("/deserialize")
    public String deserialize(@RequestBody String json) throws Exception {
        // VULNERABLE: CVE-2020-8840 - Jackson Polymorphic Deserialization
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping(); // Dangerous!
        Object obj = mapper.readValue(json, Object.class);
        return "Deserialized object: " + obj.toString();
    }
}