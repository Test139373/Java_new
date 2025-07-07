package com.vulnerable.app.controller;

import com.thoughtworks.xstream.XStream;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/xstream")
public class XStreamController {

    @PostMapping("/deserialize")
    public String deserialize(@RequestBody String xml) {
        // VULNERABLE: CVE-2021-21345 - XStream RCE
        XStream xstream = new XStream();
        Object obj = xstream.fromXML(xml);
        return "Deserialized object: " + obj.toString();
    }
}