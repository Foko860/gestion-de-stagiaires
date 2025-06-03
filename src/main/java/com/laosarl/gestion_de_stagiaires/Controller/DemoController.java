package com.laosarl.gestion_de_stagiaires.Controller;

import com.laosarl.gestion_de_stagiaires.Model.Demo;
import com.laosarl.gestion_de_stagiaires.Service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    // Endpoint to create a new Demo
    @PostMapping
    public ResponseEntity<Demo> createDemo(@RequestBody Demo demo) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(demoService.createDemo(demo));
    }
}
