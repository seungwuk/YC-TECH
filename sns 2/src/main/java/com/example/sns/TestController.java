package com.example.sns;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class TestController {

    @GetMapping("/hello")
    public ResponseEntity<String> hello(
            @RequestParam String name
    ) {
        return ResponseEntity.ok("hello" + name);
    }
}
