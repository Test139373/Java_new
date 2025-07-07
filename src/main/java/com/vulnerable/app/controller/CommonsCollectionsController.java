package com.vulnerable.app.controller;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commons-collections")
public class CommonsCollectionsController {

    @GetMapping("/rce")
    public String exploit(@RequestParam String command) {
        // VULNERABLE: CVE-2015-7501 - Commons Collections RCE
        Transformer[] transformers = new Transformer[] {
            new ConstantTransformer(Runtime.class),
            new InvokerTransformer("getMethod", 
                new Class[] {String.class, Class[].class}, 
                new Object[] {"getRuntime", new Class[0]}),
            new InvokerTransformer("invoke", 
                new Class[] {Object.class, Object[].class}, 
                new Object[] {null, new Object[0]}),
            new InvokerTransformer("exec", 
                new Class[] {String.class}, 
                new Object[] {command})
        };
        
        return "Commons Collections RCE attempted with command: " + command;
    }
}